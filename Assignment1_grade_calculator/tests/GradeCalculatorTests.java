import org.junit.jupiter.api.Test;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * JUnit 5 harness for GradeCalculator (CMSC 203 Assignment 1).
 *
 * The program reads from the keyboard and from files, so each test launches the
 * COMPILED program as a subprocess (out/GradeCalculator.class) in a temp
 * working directory, writes scripted "keyboard" input, and inspects stdout +
 * the generated grades_report.txt.
 *
 * NOTE: reconstructed on 9/14/2026 after the original file was accidentally
 * deleted; all 16 tests restored to their last known state.
 */
class GradeCalculatorTests {

    // ------------------------------------------------------------------
    // helpers
    // ------------------------------------------------------------------

    /** Plausible renderings of a number the program might print (85 -> 85 / 85.0 / 85.00). */
    static List<String> v(String s) {
        return List.of(s, s + ".0", s + ".00");
    }

    static void assertContainsAny(String haystack, List<String> needles, String msg) {
        for (String n : needles) {
            if (haystack.contains(n)) return;
        }
        fail(msg);
    }

    /** Single-string convenience overload. */
    static void assertContainsAny(String haystack, String needle, String msg) {
        assertContainsAny(haystack, List.of(needle), msg);
    }

    static void assertNotContains(String haystack, String needle, String msg) {
        assertFalse(haystack.contains(needle), msg);
    }

    /** Result of one program run: captured stdout, exit code, report file text. */
    static class RunResult {
        final String output;
        final int exitCode;
        final String report;

        RunResult(String output, int exitCode, String report) {
            this.output = output;
            this.exitCode = exitCode;
            this.report = report;
        }

        String output() { return output; }
        int exit() { return exitCode; }
        String report() { return report; }

        /** A "crash" means an unhandled exception, NOT the program's own
         *  System.exit(1) for a missing grade file. */
        boolean crashed() {
            return output.contains("Exception") || output.contains("Traceback");
        }
    }

    /** Compile output directory (javac -d out src/GradeCalculator.java), resolved to an
     *  absolute path because each subprocess runs in a temp working directory. */
    static final Path OUT_DIR = Paths.get("out").toAbsolutePath();

    /**
     * Run the program once.
     *
     * @param configContent  text written to gradeconfig.txt in a temp dir
     * @param gradeContent   text written to grades_input.txt in a temp dir
     * @param stdinLines     scripted keyboard input (config name, grade name, Y/N answers...)
     */
    static RunResult run(String configContent, String gradeContent, List<String> stdinLines) throws Exception {
        Path tmp = Files.createTempDirectory("gtest");
        Files.writeString(tmp.resolve("gradeconfig.txt"), configContent);
        Files.writeString(tmp.resolve("grades_input.txt"), gradeContent);

        String javaExe = System.getProperty("java.home") + "/bin/java";
        ProcessBuilder pb = new ProcessBuilder(javaExe, "-cp", OUT_DIR.toString(), "GradeCalculator");
        pb.directory(tmp.toFile());
        pb.redirectErrorStream(true);
        Process p = pb.start();

        try (Writer w = new OutputStreamWriter(p.getOutputStream())) {
            for (String line : stdinLines) {
                w.write(line + "\n");
                w.flush();
            }
        }

        String out = new String(p.getInputStream().readAllBytes());
        boolean done = p.waitFor(60, TimeUnit.SECONDS);
        if (!done) {
            p.destroyForcibly();
            fail("program hung:\n" + out);
        }

        Path rep = tmp.resolve("grades_report.txt");
        String report = Files.exists(rep) ? Files.readString(rep) : "";
        return new RunResult(out, p.exitValue(), report);
    }

    // ------------------------------------------------------------------
    // happy path
    // ------------------------------------------------------------------

    @Test
    void specSampleRun_noPlusMinus() throws Exception {
        // The assignment's own sample data. Expected: 94.5*0.40 + 81.25*0.30 + 88*0.30 = 88.58 -> B
        RunResult r = run("CMSC 203\n3\nProjects 40\nQuizzes 30\nExams 30\n",
                "Alex\nJohnson\nProjects\n3\n95.0 88.5 100.0\nQuizzes\n4\n80.0 90.0 85.0 70.0\nExams\n2\n92.0 84.0\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "N"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        String combined = r.output() + "\n" + r.report();
        assertContainsAny(combined, v("88.58"), "spec sample overall should be 88.58:\n" + combined);
        assertContainsAny(combined, "Base letter grade: B", "88.58 must be a B:\n" + combined);
    }

    @Test
    void plusMinusEnabled_sameData_staysB() throws Exception {
        // Same data, +/- enabled: 88.58 has decimal .58 -> between 30 and 70 -> plain B, no suffix
        RunResult r = run("CMSC 203\n3\nProjects 40\nQuizzes 30\nExams 30\n",
                "Alex\nJohnson\nProjects\n3\n95.0 88.5 100.0\nQuizzes\n4\n80.0 90.0 85.0 70.0\nExams\n2\n92.0 84.0\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "Y"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        String combined = r.output() + "\n" + r.report();
        assertContainsAny(combined, "Final letter grade: B",
                "88.58 with +/- must stay a plain B (no +/- suffix):\n" + combined);
    }

    @Test
    void perfectScore_plusMinus_neverAminus() throws Exception {
        // 100.0 with +/- enabled must be A+, NOT A- (the decimal is 0, so the
        // special case for a perfect score must kick in)
        RunResult r = run("Perfect Course\n1\nScores 100\n",
                "P\nP\nScores\n1\n100.0\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "Y"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        String combined = r.output() + "\n" + r.report();
        assertContainsAny(combined, "Final letter grade: A+",
                "100.0 with +/- must be A+:\n" + combined);
        assertNotContains(combined, "A-", "100.0 must never be A-:\n" + combined);
    }

    // ------------------------------------------------------------------
    // grade boundaries
    // ------------------------------------------------------------------

    @Test
    void boundary_90_isA() throws Exception {
        RunResult r = run("Boundary Course\n1\nScores 100\n",
                "B\nB\nScores\n1\n90.0\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "N"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        assertContainsAny(r.output() + r.report(), "Base letter grade: A",
                "90.0 must be an A (lower bound inclusive):\n" + r.output());
    }

    @Test
    void boundary_8999_isB() throws Exception {
        RunResult r = run("Boundary Course\n1\nScores 100\n",
                "B\nB\nScores\n1\n89.99\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "N"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        assertContainsAny(r.output() + r.report(), "Base letter grade: B",
                "89.99 must be a B:\n" + r.output());
    }

    @Test
    void boundary_60_isD() throws Exception {
        RunResult r = run("Boundary Course\n1\nScores 100\n",
                "B\nB\nScores\n1\n60.0\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "N"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        assertContainsAny(r.output() + r.report(), "Base letter grade: D",
                "60.0 must be a D (lower bound inclusive):\n" + r.output());
    }

    @Test
    void boundary_5999_isF() throws Exception {
        RunResult r = run("Boundary Course\n1\nScores 100\n",
                "B\nB\nScores\n1\n59.99\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "N"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        assertContainsAny(r.output() + r.report(), "Base letter grade: F",
                "59.99 must be an F:\n" + r.output());
    }

    // ------------------------------------------------------------------
    // default config
    // ------------------------------------------------------------------

    @Test
    void missingConfigFile_defaultConfig_appliedWithoutCrash() throws Exception {
        // No such config file -> default config (Projects 40 / Quizzes 30 / Exams 30),
        // grade file provides those 3 categories. Expected: 90*0.4 + 80*0.3 + 90*0.3 = 87.00 -> B
        RunResult r = run("unused\n0\n",
                "T\nS\nProjects\n2\n90.0 90.0\nQuizzes\n2\n80.0 80.0\nExams\n2\n90.0 90.0\n",
                Arrays.asList("no_such_cfg.txt", "grades_input.txt", "N"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        String combined = r.output() + "\n" + r.report();
        assertContainsAny(combined, "was used", "default config must be reported as used:\n" + combined);
        assertContainsAny(combined, v("87.00"), "overall should be 87.00:\n" + combined);
    }

    @Test
    void badWeightSum_usesDefaultConfig_withNotice() throws Exception {
        // Weights sum to 95 -> invalid -> default Projects 40 / Quizzes 30 / Exams 30
        // kicks in, so the grade file must provide those 3 categories.
        // Expected: 90*0.4 + 80*0.3 + 70*0.3 = 81.00 -> B
        RunResult r = run("Two Course\n2\nProjects 60\nQuizzes 35\n",
                "X\nY\nProjects\n1\n90.0\nQuizzes\n1\n80.0\nExams\n1\n70.0\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "N"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        String combined = r.output() + "\n" + r.report();
        assertContainsAny(combined, "was used", "default config must be reported as used:\n" + combined);
        assertContainsAny(combined, "sum of all category weights is not equal to 100",
                "the weight-sum problem must be reported:\n" + combined);
        assertContainsAny(combined, v("81.00"), "overall should be 81.00:\n" + combined);
    }

    // ------------------------------------------------------------------
    // input validation
    // ------------------------------------------------------------------

    @Test
    void invalidYN_reprompts_noCrash() throws Exception {
        // Bad answers ("x", "z") must not crash and must re-prompt until a valid Y/N
        RunResult r = run("CMSC 203\n1\nProjects 100\n",
                "G\nH\nProjects\n1\n92.5\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "x", "z", "Y"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        String combined = r.output() + "\n" + r.report();
        assertContainsAny(combined, "Final letter grade: A",
                "92.5 with +/- must be a plain A (decimal 50, no suffix):\n" + combined);
    }

    @Test
    void negativeScores_skipped() throws Exception {
        RunResult r = run("Neg Course\n1\nScores 100\n", "N\nS\nScores\n3\n80.0 -5.0 90.0\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "N"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        String combined = r.output() + "\n" + r.report();
        assertContainsAny(combined, v("85"), "avg of valid scores (80+90)/2 = 85 expected");
        assertContainsAny(combined, "Base letter grade: B", "85 must be a B");
    }

    @Test
    void allScoresInvalid_zeroAndF() throws Exception {
        RunResult r = run("Neg Course\n1\nScores 100\n", "N\nS\nScores\n3\n-1.0 -2.0 -3.0\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "N"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        String combined = r.output() + "\n" + r.report();
        assertContainsAny(combined, v("0"), "category average should be 0");
        assertContainsAny(combined, "Base letter grade: F", "0 overall must be an F");
    }

    @Test
    void firstCategoryAllInvalid_nextCategoryStillProcessed_customConfig() throws Exception {
        // Regression test: after an all-invalid category, the reader must still be
        // in sync for the NEXT category. If it isn't, the second category is
        // misread (false "does not match" / wrong average).
        // Expected: Projects contributes 0 (all invalid), Quizzes (80+90)/2 = 85 x 45% = 38.25 -> F
        RunResult r = run("Two Course\n2\nProjects 55\nQuizzes 45\n",
                "X\nY\nProjects\n1\n200.0\nQuizzes\n2\n80.0 90.0\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "N"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        String combined = r.output() + "\n" + r.report();
        assertContainsAny(combined, "All grades in category Projects",
                "first category should be reported as all-invalid:\n" + combined);
        assertNotContains(combined, "does not match",
                "second category must NOT be flagged as mismatched (desync):\n" + combined);
        assertContainsAny(combined, v("38.25"),
                "overall should be 38.25 (0 + 85*0.45):\n" + combined);
    }

    @Test
    void firstCategoryAllInvalid_nextCategoryStillProcessed_defaultConfig() throws Exception {
        // Same regression test, but through the DEFAULT config path (bad weight sum
        // forces the default 3 categories, so the grade file has all 3).
        // Expected: Projects 0, Quizzes 85*30% = 25.5, Exams 90*30% = 27 -> 52.5 -> F
        RunResult r = run("Two Course\n2\nProjects 60\nQuizzes 35\n",
                "X\nY\nProjects\n1\n200.0\nQuizzes\n2\n80.0 90.0\nExams\n1\n90.0\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "N"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        String combined = r.output() + "\n" + r.report();
        assertContainsAny(combined, "was used", "default config should be in use:\n" + combined);
        assertNotContains(combined, "does not match",
                "no category should be flagged as mismatched (desync):\n" + combined);
        assertContainsAny(combined, v("52.5"),
                "overall should be 52.5 (0 + 25.5 + 27):\n" + combined);
    }

    // ------------------------------------------------------------------
    // error handling
    // ------------------------------------------------------------------

    @Test
    void categoryMismatch_reportsError_noCrash() throws Exception {
        // "Project" in the grade file vs "Projects" in the config -> error reported,
        // the bad block is skipped, and the remaining categories are still processed.
        // Expected: 0 + 80*0.3 + 90*0.3 = 51.00 -> F
        RunResult r = run("CMSC 203\n3\nProjects 40\nQuizzes 30\nExams 30\n",
                "E\nF\nProject\n2\n90.0 90.0\nQuizzes\n2\n80.0 80.0\nExams\n2\n90.0 90.0\n",
                Arrays.asList("gradeconfig.txt", "grades_input.txt", "N"));
        assertFalse(r.crashed(), "program crashed:\n" + r.output());
        String combined = r.output() + "\n" + r.report();
        assertContainsAny(combined, "does not match",
                "mismatch must be reported:\n" + combined);
        assertContainsAny(combined, v("51.00"),
                "remaining categories should still be graded (51.00):\n" + combined);
    }

    @Test
    void nonexistentGradeFile_gracefulExitWithError() throws Exception {
        // Missing GRADE file (config is fine) -> clean error + exit, no stack trace
        RunResult r = run("CMSC 203\n3\nProjects 40\nQuizzes 30\nExams 30\n",
                "unused\n0\n",
                Arrays.asList("gradeconfig.txt", "no_such_grades.txt", "N"));
        assertFalse(r.crashed(), "program must not dump a stack trace:\n" + r.output());
        assertContainsAny(r.output(), "File not found",
                "missing grade file must produce a clean error message:\n" + r.output());
    }
}
