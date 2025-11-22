// Web module uses Node.js/SvelteKit, not Gradle
// Build and run commands:
// npm install
// npm run dev
// npm run build

tasks.register("install") {
    group = "build"
    description = "Install Node.js dependencies"
    doLast {
        exec {
            workingDir = projectDir
            commandLine("npm", "install")
        }
    }
}

tasks.register("dev") {
    group = "application"
    description = "Run SvelteKit dev server"
    doLast {
        exec {
            workingDir = projectDir
            commandLine("npm", "run", "dev")
        }
    }
}

tasks.register("buildWeb") {
    group = "build"
    description = "Build SvelteKit app"
    doLast {
        exec {
            workingDir = projectDir
            commandLine("npm", "run", "build")
        }
    }
}
