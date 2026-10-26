import dev.nucleusframework.desktop.application.dsl.AppImageCategory
import dev.nucleusframework.desktop.application.dsl.DmgContentType
import dev.nucleusframework.desktop.application.dsl.TargetFormat

val osName = System.getProperty("os.name").lowercase()


plugins {
    alias(libs.plugins.nucleusframework)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.plugin)
}


group = "dev.iln.cobblemongo"
version = providers.environmentVariable("RELEASE_VERSION").map { it.removePrefix("v") }.getOrElse("1.0.0")


repositories {
    mavenCentral()
    google()

    maven("https://jitpack.io")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}


dependencies {
    implementation(libs.nucleus.application)
    implementation(libs.nucleus.decorated.window.tao)
    implementation(libs.nucleus.core)
    implementation(libs.nucleus.updater)
    implementation(libs.nucleus.autolaunch)
    implementation(libs.nucleus.system.info)

    implementation(compose.desktop.currentOs)
    implementation(compose.components.resources)
    implementation(libs.navigation3.ui)

    implementation(libs.coroutines.swing)
    implementation(libs.coroutines.core)
    implementation(libs.serialization.json)

    implementation(libs.flowarg.azuljavadownloader)
    implementation(libs.flowarg.flowupdater)
    implementation(libs.flowarg.openlauncherlib)
    //implementation(libs.kpresence)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
}


kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
        vendor.set(JvmVendorSpec.AZUL)
    }
}


compose.resources {
    publicResClass = true
    generateResClass = always
    packageOfResClass = "dev.iln.cobblemongo.resources"
}


nucleus.application {
    mainClass = "dev.iln.cobblemongo.CobblemonGoLauncherKt"

    buildTypes.release.proguard {
        isEnabled.set(false)
    }

    nativeDistributions {
        appName = "CobblemonGo"
        description = "Official launcher of CobblemonGo"
        copyright = "© 2026 CobblemonGo"
        homepage = "https://cobblemongo.net"

        packageName = "CobblemonGo-Launcher"
        packageVersion = project.version.toString()
        targetFormats(TargetFormat.Dmg, TargetFormat.Nsis, TargetFormat.AppImage, TargetFormat.Zip)

        cleanupNativeLibs = true
        enableAotCache = true

        artifactName = when {
            "win" in osName -> $$"$${appName}-${version}.${ext}"
            "mac" in osName -> $$"$${appName}-${version}-mac-${arch}.${ext}"
            else -> $$"$${appName}-${version}-linux.${ext}"
        }

        macOS {
            bundleID = "dev.iln.cobblemongo"
            dockName = "CobblemonGo"
            appCategory = "public.app-category.games"
            minimumSystemVersion = "14.0"
            iconFile.set(project.file("packaging/macos/icon.icns"))

            dmg {
                title = $$"${productName} ${version}"
                iconSize = 128
                window { x = 400; y = 100; width = 540; height = 380 }
                background.set(project.file("packaging/macos/dmg-bg.tiff"))
                content(x = 130, y = 220, type = DmgContentType.File, name = "CobblemonGo.app")
                content(x = 410, y = 220, type = DmgContentType.Link, path = "/Applications")
            }
        }

        windows {
            upgradeUuid = "93d7ba25-a5e0-41ce-84d6-a62b2f1afd71"
            menu = true
            menuGroup = "CobblemonGo"
            iconFile.set(project.file("packaging/windows/icon.ico"))

            nsis {
                oneClick = false
                allowElevation = true
                perMachine = true
                allowToChangeInstallationDirectory = true
                createDesktopShortcut = true
                runAfterFinish = true

                installerIcon.set(project.file("packaging/windows/installer.ico"))

                shortcutName = "CobblemonGo"

                multiLanguageInstaller = true
                installerLanguages = listOf("fr_FR", "en_US", "de_DE", "es_ES", "pt_BR")

                installerHeader.set(project.file("packaging/windows/header.bmp"))
                installerSidebar.set(project.file("packaging/windows/sidebar.bmp"))
            }
        }

        linux {
            shortcut = true
            appCategory = "Game"
            menuGroup = "Game"
            iconFile.set(project.file("packaging/linux/icon.png"))

            appImage {
                category = AppImageCategory.Game
                genericName = "CobblemonGo"
                synopsis = "Official launcher of CobblemonGo"
            }
        }
    }
}