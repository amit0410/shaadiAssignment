package com.example.shaadi.feature.ui.main.shaadi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.shaadi.R
import com.example.shaadi.feature.MatchStatus
import com.example.shaadi.feature.contract.UserModel
import kotlinx.android.synthetic.main.card_view_list_item.view.*

class ShaadiAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserModel>() {

        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.login?.username == newItem.login?.username
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return BrandViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.card_view_list_item,
                    parent,
                    false
                ),
                interaction
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BrandViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<UserModel>) {
        differ.submitList(list)
    }

    class BrandViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: UserModel) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onAccept(adapterPosition, item)
            }
            profilePic.load(item.picture?.medium)
            name.text = "${item.name?.first} ${item.name?.last}"
            dob.text = "(AGE : ${item.dob?.age} yrs)"
            street.text = "${item.location?.street?.number}, ${item.location?.street?.name}"
            location.text = "${item.location?.city}, ${item.location?.state}, ${item.location?.country}"
            if(item.status.equals(MatchStatus.NotResponded.toString())){
                status.visibility = View.GONE
                hideOnResponse.visibility = View.VISIBLE
            }else{
                status.visibility = View.VISIBLE
                hideOnResponse.visibility = View.GONE
            }

            acceptButton.setOnClickListener{
                hideOnResponse.visibility = View.GONE
                status.visibility = View.VISIBLE
                status.text = MatchStatus.Accepted.toString()
                interaction?.onAccept(adapterPosition, item)
            }
            declinedButton.setOnClickListener {
                hideOnResponse.visibility = View.GONE
                status.visibility = View.VISIBLE
                status.text = MatchStatus.Declined.toString()
                interaction?.onReject(adapterPosition,item)
            }
            status.setText(item.status)
        }
    }

    interface Interaction {
        fun onAccept(position: Int, item: UserModel)
        fun onReject(position: Int,item: UserModel)
    }
}