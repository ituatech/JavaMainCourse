package com.it_uatech.gc_logging;

enum MemoryTypes {
    YOUNG("end of minor GC"),
    OLD("end of major GC");

    private String gcAction;

    MemoryTypes(String gcAction) {
        this.gcAction = gcAction;
    }

    public String getGcAction(){
        return gcAction;
    }
}
