import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

// 插件依赖仓库源
pluginManagement {
    fun RepositoryHandler.maven(url: String, action: (MavenArtifactRepository.() -> Unit)? = null) = maven {
        setUrl(url)
        action?.invoke(this)
        isAllowInsecureProtocol = true
    }

    fun RepositoryHandler.hikvisionMaven() {
        maven("http://af-proxy.hikvision.com.cn:80/artifactory/maven-aliyun-gradle-plugin-remote/")
        maven("http://af-proxy.hikvision.com.cn:80/artifactory/maven-aliyun-central-remote/")
        maven("http://af-proxy.hikvision.com.cn:80/artifactory/maven-aliyun-google-remote/")
        maven("http://af-proxy.hikvision.com.cn:80/artifactory/maven-aliyun-proxy/")
    }

    repositories {
        hikvisionMaven()
    }
}

gradle.projectsLoaded {
    // 项目依赖仓库源
    fun RepositoryHandler.maven(url: String, action: (MavenArtifactRepository.() -> Unit)? = null) = maven {
        setUrl(url)
        action?.invoke(this)
        isAllowInsecureProtocol = true
    }

    fun RepositoryHandler.hikvisionMaven() {
        maven("http://af-proxy.hikvision.com.cn:80/artifactory/maven-aliyun-central-remote/")
        maven("http://af-proxy.hikvision.com.cn:80/artifactory/maven-aliyun-google-remote/")
        maven("http://af-proxy.hikvision.com.cn:80/artifactory/maven-aliyun-proxy/")
    }
    rootProject.buildscript {
        repositories {
            hikvisionMaven()
        }
    }
    allprojects {
        repositories {
            hikvisionMaven()
        }
    }
}

rootProject.name = "myApplication"
include(":app")

