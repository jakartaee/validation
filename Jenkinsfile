/*
 * Jakarta Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */

@Library('releng-pipeline') _

// Avoid running the pipeline on branch indexing
if (currentBuild.getBuildCauses().toString().contains('BranchIndexingCause')) {
	print "INFO: Build skipped due to trigger being Branch Indexing"
	currentBuild.result = 'NOT_BUILT'
	return
}

pipeline {
	agent none
	options {
		buildDiscarder logRotator(daysToKeepStr: '10', numToKeepStr: '10')
		disableConcurrentBuilds(abortPrevious: true)
		overrideIndexTriggers(false)
	}
	stages {
		stage('Build') {
			matrix {
				axes {
					axis {
						name 'JDK_FOR_BUILD'
						values 'openjdk-jdk25-latest', 'openjdk-jdk21-latest'
					}
				}
				stages {
					stage("Build") {
						agent {
							label 'basic'
						}
						stages {
							stage('Build') {
								steps {
									withMaven(jdk: env.JDK_FOR_BUILD, maven: 'apache-maven-3.9.11',
											mavenLocalRepo: env.WORKSPACE_TMP + '/.m2repository',
											options: [
													artifactsPublisher(disabled: true),
													junitPublisher(disabled: true)
											]) {
										sh './mvnw clean install -Psnapshots'
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
