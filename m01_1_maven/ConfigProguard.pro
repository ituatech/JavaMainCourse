#java -jar proguard\proguard-base-6.1.1.jar @ConfigProguard.pro

-injars       target\L01.1-maven.jar(!META-INF/maven/**)
-outjars      target\L01.1-maven-small.jar
-libraryjars  <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class)
-printmapping target\L01.1-maven.map
-printseeds   target\L01.1-maven.txt

-keep public class com.it_uatech.Main {
                            public static void main(java.lang.String[]);}

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,
                            SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

-dontnote
-dontwarn
