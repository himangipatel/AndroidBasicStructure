package com.androidbasicstructure.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.v7.util.DiffUtil;
import android.view.View;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;

/**
 * Created by Himangi on 8/1/19
 */
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String name;
    private String email;

    public static DiffUtil.ItemCallback<User> userItemCallback = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(User oldItem, User newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(User oldItem, User newItem) {
            return oldItem.equals(newItem);
        }
    };

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void displayUserData(View view) {
        if (!isEmpty(name.trim())) {
            Toast.makeText(view.getContext(), name.concat(" ").concat(email), Toast.LENGTH_SHORT).show();
        }
    }

    public void onUsernameTextChanged(CharSequence text) {
        // TODO do something with text
    }
}
