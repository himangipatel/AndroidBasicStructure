package com.androidbasicstructure.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.androidbasicstructure.R;
import com.androidbasicstructure.adapter.UserListAdapter;
import com.androidbasicstructure.annotations.Layout;
import com.androidbasicstructure.base.BaseView;
import com.androidbasicstructure.base.MVVMActivity;
import com.androidbasicstructure.databinding.ActivityUserListingBinding;
import com.androidbasicstructure.db.DatabaseHandler;
import com.androidbasicstructure.interfaces.ItemClickListener;
import com.androidbasicstructure.models.User;
import com.androidbasicstructure.presenter.UserListingViewModel;
import com.androidbasicstructure.utils.AppUtils;

import java.util.List;

import static android.text.TextUtils.isEmpty;

@Layout(R.layout.activity_user_listing)
public class UserListingActivity extends MVVMActivity<UserListingViewModel,
        BaseView<List<User>>> implements BaseView<List<User>> {

    private ActivityUserListingBinding binding;
    private User editableUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        setToolBarVisibility(View.GONE);
        viewModelDemo();
        clickableViews(new View[]{binding.btnAdd});
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
                // usersListingAdapter.setItems(users);

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
        }
    }

    @NonNull
    @Override
    public UserListingViewModel createPresenter() {
        return ViewModelProviders.of(this).get(UserListingViewModel.class);
    }

    @NonNull
    @Override
    public BaseView<List<User>> attachView() {
        return this;
    }


    @Override
    public void onFailure(String message) {

    }
}
