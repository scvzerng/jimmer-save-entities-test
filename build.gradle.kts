plugins {
    kotlin("jvm") version "2.2.0"
    id("org.springframework.boot") version "3.2.4"
    id("com.google.devtools.ksp") version "2.2.0-2.0.2"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.scvzerng"
version = "1.0-SNAPSHOT"

val jimmerVersion = "0.9.106"
val springbootVersion = "3.2.4"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    api("org.springframework.boot:spring-boot-starter-parent:$springbootVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:${jimmerVersion}")
    implementation("org.babyfish.jimmer:jimmer-core:${jimmerVersion}")
    compileOnly("org.babyfish.jimmer:jimmer-sql:${jimmerVersion}")
    ksp("org.babyfish.jimmer:jimmer-ksp:${jimmerVersion}")
    implementation("org.postgresql:postgresql:42.6.2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}

tasks.bootJar {
    enabled = true
}

tasks.jar {
    enabled = false
}


