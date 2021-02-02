package com.example.notizieguardian

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class NewsAdapter(val listaRisultati: MutableList<Data>) : BaseAdapter() {
    override fun getCount(): Int {
        return listaRisultati.size
    }

    override fun getItem(position: Int): Any {
        return listaRisultati[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent?.context
        var rowView = convertView
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if(rowView == null) {
            rowView = inflater.inflate(R.layout.item_news_layout,parent,false)
        }
        val item = listaRisultati[position]
        val title = rowView?.findViewById<TextView>(R.id.webTitleField)
        title?.text = item.webTitle
        val address = rowView?.findViewById<ImageView>(R.id.showWebLinkContent)
        address?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.webUri))
            context?.startActivity(intent)
        }
        return rowView!!
    }
}