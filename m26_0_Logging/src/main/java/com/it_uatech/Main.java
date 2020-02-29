package com.it_uatech;

import com.it_uatech.subpackage1.Package1Class;
import com.it_uatech.subpackage1.subpackage.SubpackageClass;
import com.it_uatech.subpackage2.Package2Class;

public class Main {
    public static void main(String[] args) {
        SubpackageClass subpackageClass = new SubpackageClass();
        Package1Class package1Class = new Package1Class();
        Package2Class package2Class = new Package2Class();
        MainPackageClass mainPackageClass = new MainPackageClass();

        subpackageClass.doLogging();
        package1Class.doLogging();
        package2Class.doLogging();
        mainPackageClass.doLogging();
    }
}
