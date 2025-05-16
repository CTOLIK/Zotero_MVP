package com.example.zoteromvp.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequestUserPart {
    @SerializedName("library")
    @Expose
    private boolean library = true;

    @SerializedName("notes")
    @Expose
    private boolean notes = true;

    @SerializedName("write")
    @Expose
    private boolean write = true;

    @SerializedName("files")
    @Expose
    private boolean files = true;

    public boolean isLibrary() {
        return library;
    }

    public void setLibrary(boolean library) {
        this.library = library;
    }

    public boolean isNotes() {
        return notes;
    }

    public void setNotes(boolean notes) {
        this.notes = notes;
    }

    public boolean isWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public boolean isFiles() {
        return files;
    }

    public void setFiles(boolean files) {
        this.files = files;
    }
}
