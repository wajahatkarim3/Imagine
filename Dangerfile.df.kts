@file:DependsOn("com.gianluz:danger-kotlin-android-lint-plugin:0.1.0")

import com.gianluz.dangerkotlin.androidlint.AndroidLint
import systems.danger.kotlin.*

register plugin AndroidLint

danger(args) {

    val allSourceFiles = git.modifiedFiles + git.createdFiles
    val changelogChanged = allSourceFiles.contains("CHANGELOG.md")
    val sourceChanges = allSourceFiles.firstOrNull { it.contains("src") }

    onGitHub {
        message("Thanks @${pullRequest.user.login} 👍")
        // val isTrivial = pullRequest.title.contains("#trivial")

        // Changelog
        //if (!isTrivial && !changelogChanged && sourceChanges != null) {
        //    warn(WordUtils.capitalize("any changes to library code should be reflected in the Changelog.\n\nPlease consider adding a note there and adhere to the [Changelog Guidelines](https://github.com/Moya/contributors/blob/master/Changelog%20Guidelines.md)."))
        //}

        // Encourage contributors to write useful descriptions
        if (pullRequest.body.length < 20) {
            warn("Please provide a Pull Request description ...")
        }

        // Big PR Check
        if ((pullRequest.additions ?: 0) - (pullRequest.deletions ?: 0) > 300) {
            warn("Big PR, try to keep changes smaller if you can")
        }

        // Work in progress check
        if (pullRequest.title.contains("WIP", false)) {
            warn("PR is classed as Work in Progress")
        }

        // Default report
        AndroidLint.report("./build/reports/lint-results-debug.xml")
    }
}