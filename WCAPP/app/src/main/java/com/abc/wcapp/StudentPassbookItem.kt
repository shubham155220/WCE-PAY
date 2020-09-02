package com.abc.wcapp

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.parent_pay_pass_item.view.*

class StudentPassbookItem( var sname:String,var grpname:String,var amt:String,var note:String,var date:String): Item<ViewHolder>(){

    override fun getLayout(): Int {
        return R.layout.parent_pay_pass_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.sender_p_name.text = sname
        viewHolder.itemView.sender_batch.text = grpname
        viewHolder.itemView.send_amount.text= amt
        viewHolder.itemView.sender_note.text= note
        viewHolder.itemView.send_date.text = date
    }

}