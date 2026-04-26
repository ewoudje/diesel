import dev.scaffoldit.hytale.wire.HytaleManifest

rootProject.name = "dieselgame"

plugins {
    id("dev.scaffoldit") version "0.2.+"
}

hytale {
    usePatchline("release")
    useVersion("latest")

    repositories {
        // Any external repositories besides: MavenLocal, MavenCentral, HytaleMaven, and CurseMaven
    }

    dependencies {
        // Any external dependency you also want to include
    }

    manifest {
        Group = "nsane"
        Name = "DieselPlugin"
        Main = "com.nsane.diesel.DieselPlugin"
        IncludesAssetPack = true
        Description = "Epic robot adventure play now free"
        Authors = listOf(
            HytaleManifest.Author("ewoudje", "me@ewoudje.com", "https://ewoudje.com"),
            HytaleManifest.Author("rainyatrium", "ana@rainyatrium.net", "https://rainyatrium.net"),
            HytaleManifest.Author(Name = "ComicalSomber", Url = "https://github.com/ComicalSomber"),
            HytaleManifest.Author(Name = "Noisyrejects", Url = "https://sola.nekoweb.org/"),
            HytaleManifest.Author(Name = "Nahu Pyrope", Url = "https://www.youtube.com/nahupyrope"),
        )
    }
}