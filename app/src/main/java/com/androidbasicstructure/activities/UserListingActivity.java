package com.androidbasicstructure.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.androidbasicstructure.R;
import com.androidbasicstructure.adapter.UserListAdapter;
import com.androidbasicstructure.annotations.Layout;
import com.androidbasicstructure.base.MVVMActivity;
import com.androidbasicstructure.databinding.ActivityUserListingBinding;
import com.androidbasicstructure.db.DatabaseHandler;
import com.androidbasicstructure.interfaces.ItemClickListener;
import com.androidbasicstructure.models.User;
import com.androidbasicstructure.utils.AppUtils;
import com.androidbasicstructure.viewmodel.UserListingViewModel;

import java.util.List;

import static android.text.TextUtils.isEmpty;

@Layout(R.layout.activity_user_listing)
public class UserListingActivity extends MVVMActivity<UserListingViewModel> {

    private ActivityUserListingBinding binding;
    private User editableUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        binding = getBinding();
        setToolBarVisibility(View.VISIBLE);
        viewModelDemo();
        clickableViews(new View[]{binding.btnAdd});
        setTootBartVRight("Move to Fragment Demo");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void viewModelDemo() {
        binding.rvUserList.setLayoutManager(new LinearLayoutManager(this));
        final UserListAdapter usersListingAdapter = new UserListAdapter();
        usersListingAdapter.setItemClickListener(clickListener);
        // final UsersListingAdapter usersListingAdapter = new UsersListingAdapter();

        getViewModel().getUserList().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> users) {
                usersListingAdapter.submitList(users);
            }
        });

        binding.rvUserList.setAdapter(usersListingAdapter);
    }

    private ItemClickListener<User> clickListener = new ItemClickListener<User>() {
        @Override
        public void onClick(User user, int position, View view) {
            switch (view.getId()) {
                case R.id.ivDelete:
                    DatabaseHandler
                            .getInstance(getActivity())
                            .getUserDao()
                            .delete(user);
                    break;
                case R.id.ivEdit:
                    editableUser = user;
                    binding.etEmail.setText(user.getEmail());
                    binding.etName.setText(user.getName());
                    binding.btnAdd.setText("Update");
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btnAdd:
                if (isEmpty(AppUtils.getText(binding.etName)) || isEmpty(AppUtils.getText(binding.etEmail))) {
                    return;
                }

                if (binding.btnAdd.getText().equals("Update")) {
                    editableUser.setEmail(AppUtils.getText(binding.etEmail));
                    editableUser.setName(AppUtils.getText(binding.etName));
                    DatabaseHandler
                            .getInstance(getActivity())
                            .getUserDao()
                            .update(editableUser);
                    binding.btnAdd.setText("Add");
                    binding.etName.setText("");
                    binding.etEmail.setText("");
                } else {
                    DatabaseHandler
                            .getInstance(getActivity())
                            .getUserDao()
                            .insert(new User(AppUtils.getText(binding.etName),
                                    AppUtils.getText(binding.etEmail)));
                }


                break;
            case R.id.tvToolbarRight:
                Intent intent = new Intent(UserListingActivity.this, CalculatorActivity.class);
                startActivity(intent);
                break;
        }
    }

    @NonNull
    @Override
    public UserListingViewModel createViewModel() {
        return ViewModelProviders.of(this).get(UserListingViewModel.class);
    }

}
