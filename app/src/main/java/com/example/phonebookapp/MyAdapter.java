package com.example.phonebookapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebookapp.databinding.ItemCardBinding;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.UserViewHolder> {

    private final Context context;
    private final ArrayList<User> userArrayList;

    public MyAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }
    public void updateUsers(List<User> newUsers) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return userArrayList.size();
            }

            @Override
            public int getNewListSize() {
                return newUsers.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return userArrayList.get(oldItemPosition).getPhoneNumber().equals(newUsers.get(newItemPosition).getPhoneNumber());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return userArrayList.get(oldItemPosition).equals(newUsers.get(newItemPosition));
            }
        });
        userArrayList.clear();
        userArrayList.addAll(newUsers);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initializes the ViewHolder and Inflates the Item layout

        ItemCardBinding binding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.item_card,
                        parent,
        false
    );


        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        // bind data to an existing ViewHolder
        //Populates the Views in the ViewHolder wuth data from
        // the dataset

        User currentUser = userArrayList.get(position);
        holder.itemCardBinding.setUser(currentUser);
        holder.itemCardBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    // View Holder : cache references to the
    // individual views withing an item layout
    // of a RecyclerView list or grid.




    public class UserViewHolder extends RecyclerView.ViewHolder{
         ItemCardBinding itemCardBinding;
        public UserViewHolder(ItemCardBinding itemCardBinding) {
            super(itemCardBinding.getRoot());
            this.itemCardBinding = itemCardBinding;

            // Handling Click Events on RecyclerView Items:
            itemCardBinding.getRoot().setOnClickListener(v -> {
                // getting the clicked item position
                int position =  getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    User clickedUser = userArrayList.get(position);
                    // Example action: Show Toast
                    Toast.makeText(context, "Clicked: " + clickedUser.getUsername(), Toast.LENGTH_SHORT).show();

                    // Or you can implement a listener callback for real usage
                }
            });
        }
    }

}
