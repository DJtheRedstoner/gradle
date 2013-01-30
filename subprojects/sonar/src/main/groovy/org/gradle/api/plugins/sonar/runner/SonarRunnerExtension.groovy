/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.plugins.sonar.runner

/**
 * An extension for configuring the <a href="http://docs.codehaus.org/display/SONAR/Analyzing+with+Sonar+Runner">
 * Sonar Runner</a>. The extension is added to all projects that have the {@code sonar-runner}
 * plugin applied, and all of their subprojects.
 *
 * <p>Example usage:
 *
 * <pre autoTested=''>
 * sonarRunner {
 *     skipProject = false // this is the default
 *
 *     sonarProperties {
 *         property "sonar.host.url", "http://my.sonar.server" // adding a single property
 *         properties mapOfProperties // adding multiple properties at once
 *         properties["sonar.sources"] += sourceSets.other.java.srcDirs // manipulating an existing property
 *     }
 * }
 * </pre>
 */
class SonarRunnerExtension {
    /**
     * The directory where Sonar Runner keeps downloaded files necessary
     * for its execution. Defaults to {@code $buildDir/sonar/bootstrap}.
     *
     * <p>This property is only relevant for the "root" project of a Sonar run
     * (i.e. the project that has the {@code sonar-runner} plugin applied),
     * but not for its subprojects.
     */
    File bootstrapDir

    /**
     * Tells if the project will be excluded from analysis. Defaults to {@code false}.
     */
    boolean skipProject

    /**
     * Adds a configuration block that configures Sonar properties for the associated Gradle project.
     * <em>Global</em> Sonar properties (e.g. database connection settings) have to be set on the
     * "root" project of the Sonar run. This is the project that has the {@code sonar-runner} plugin applied.
     *
     * <p>The specified code block delegates to an instance of {@code SonarProperties}.
     * Evaluation of the block is deferred until {@code sonarRunner.sonarProperties} is requested.
     * Hence it is safe to reference other Gradle model properties from inside the block.
     *
     * <p>Sonar properties can also be set via system properties (and therefore from the command line).
     * This is mainly useful for global Sonar properties like database credentials.
     * Every system property starting with {@code "sonar."} is automatically set on the "root" project of the
     * Sonar run (i.e. the project that has the {@code sonar-runner} plugin applied). System properties take
     * precedence over properties declared in build scripts.
     *
     * @param block a configuration block that configures Sonar properties for the associated Gradle project
     */
    void sonarProperties(Closure<?> block) {
        sonarPropertiesBlocks << block
    }

    /**
     * The configuration blocks for Sonar properties. This property should not be used directly.
     * Instead, use the {@link #sonarProperties(groovy.lang.Closure)} method.
     */
    List<Closure<?>> sonarPropertiesBlocks = []
}
