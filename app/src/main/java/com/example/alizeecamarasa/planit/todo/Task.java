package com.example.alizeecamarasa.planit.todo;

/**
 * Created by alizeecamarasa on 23/02/15.
 */
public class Task implements Comparable {
    private int id;
    private String content;
    // 0 => no, 1 => yes
    private int checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        if (checked == 0)
            return false;
        else
            return true;
    }

    public void setChecked(boolean checked) {
        if (checked == false)
            this.checked = 0;
        else
            this.checked = 1;
    }

    // cas où la tâche est cochée : return 2 => descendre, return 0 => rien
    // cas où la tâche est décochée : return 1 => monter, return 0 = > rien
    @Override
    public int compareTo(Object another) {
        Task other = (Task) another;
        if (isChecked()==false && other.isChecked()==true )
            return 1;
        else if (isChecked()== true && other.isChecked()==false)
            return 2;
        return 0;
    }
}
