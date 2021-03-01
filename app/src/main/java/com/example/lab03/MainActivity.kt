package com.example.lab03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface ClickListener {

    fun onItemClicked(view: View, position: Int)

}
interface LongClickListener {

    fun onItemLongClicked(view: View, position: Int)

}
class Notice
(
        var image: Int, var title: String, var description: String) {

}
class MainActivity : AppCompatActivity() {

    var list: RecyclerView? = null
    var adapter0: AdapterCustom? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    var isActionMode = false

    companion object {
        var notices:ArrayList<Notice>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnAdd = findViewById<Button>(R.id.agregar)

        notices = ArrayList()

        notices?.add(Notice(R.drawable.imagen1, "Informacion", "Esta es una descripcion de la noticia."))
        notices?.add(Notice(R.drawable.imagen2, "Informacion", "Esta es una descripcion de la noticia."))
        notices?.add(Notice(R.drawable.imagen3, "Informacion", "Esta es una descripcion de la noticia."))

        refreshLayout()

        btnAdd.setOnClickListener{
            notices?.add(Notice(R.drawable.imagen3, "Informacion", "Esta es una descripcion de la noticia."))

            refreshLayout()
        }
        adapter0 = AdapterCustom(notices!!, object:ClickListener{
            override fun onItemClicked(view: View, position: Int) {
                notices?.removeAt(position)

                refreshLayout()
            }
        }, object: LongClickListener{
            override fun onItemLongClicked(view: View, position: Int) {

                // Aquí ya se actualizará la noticia
                notices?.get(position)?.image = R.drawable.imagen3
                notices?.get(position)?.title = "Noticia actualizada"
                notices?.get(position)?.description = "Descripcion de la noticia"

                refreshLayout()
            }
        })

        list?.adapter = adapter0
    }

    private fun refreshLayout(){
        list = findViewById(R.id.lista)
        list?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)
        list?.layoutManager = layoutManager
    }
}


class AdapterCustom(items:ArrayList<Notice>, var listener:ClickListener, var longClickListener: LongClickListener): RecyclerView.Adapter<AdapterCustom.ViewHolder>() {

    var items:ArrayList<Notice>? = null
    var viewHolder: ViewHolder? = null

    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCustom.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_notice, parent, false)
        viewHolder = ViewHolder(view, listener, longClickListener)

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: AdapterCustom.ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.image?.setImageResource(item?.image!!)
        holder.title?.text = item?.title
        holder.description?.text = item?.description


    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    class ViewHolder(var view: View, listener: ClickListener, longClickListener: LongClickListener): RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        var image: ImageView? = null
        var title: TextView? = null
        var description: TextView? = null
        var listener: ClickListener? = null
        var longListener: LongClickListener? = null

        init {
            image = view.findViewById(R.id.ivImage)
            title = view.findViewById(R.id.tvTitle)
            description = view.findViewById(R.id.tvDescription)

            this.listener = listener
            this.longListener = longClickListener

            view.setOnClickListener(this)
            view.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onItemClicked(v!!, adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            this.longListener?.onItemLongClicked(v!!, adapterPosition)
            return true
        }
    }
}
