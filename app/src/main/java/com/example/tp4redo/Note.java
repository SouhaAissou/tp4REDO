package com.example.tp4redo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

//public class Note implements Parcelable {
//
//    private String title;
//    private String content;
//    private String created;
//    private String noteText;
//
//    public Note(String title, String content, String created, String noteText) {
//        this.title = title;
//        this.content = content;
//        this.created = created;
//        this.noteText = noteText;
//    }
//
//    protected Note(Parcel in) {
//        title = in.readString();
//        content = in.readString();
//        created = in.readString();
//    }
//
//    public static final Creator<Note> CREATOR = new Creator<Note>() {
//        @Override
//        public Note createFromParcel(Parcel in) {
//            return new Note(in);
//        }
//
//        @Override
//        public Note[] newArray(int size) {
//            return new Note[size];
//        }
//    };
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//
//    public String getNoteText() {
//        return noteText;
//    }
//    public String getCreated() {
//        return created;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(@NonNull Parcel parcel, int i) {
//        parcel.writeString(title);
//        parcel.writeString(content);
//        parcel.writeString(created);
//    }
//}
public class Note implements Parcelable {

    private String title;
    private String content;
    private String created;
    private String noteText;

    public Note(String title, String content, String created, String noteText) {
        this.title = title;
        this.content = content;
        this.created = created;
        this.noteText = noteText;
    }

    protected Note(Parcel in) {
        title = in.readString();
        content = in.readString();
        created = in.readString();
        noteText = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getNoteText() {
        return noteText;
    }

    public String getCreated() {
        return created;
    }

    // Setter method for note content
    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
    public void setContent(String noteText) {
        this.content = noteText;
    }

    // Setter method for note content display
    public void setNoteTextDisplay(String noteTextDisplay) {
        this.content = noteTextDisplay;
    }

    // Setter method for note creation date
    public void setCreationDate(String creationDate) {
        this.created = creationDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(created);
        parcel.writeString(noteText);
    }
}
