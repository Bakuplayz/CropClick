mvn deploy:deploy-file -Dfile=./local-maven-repo/MockBukkit-1.12.jar -DgroupId=be.seeseemelk.mockbukkit -DartifactId=MockBukkit -Dversion=1.12 -Dpackaging=jar -Durl=file:./local-maven-repo/ -DrepositoryId=local-maven-repo -DupdateReleaseInfo=true