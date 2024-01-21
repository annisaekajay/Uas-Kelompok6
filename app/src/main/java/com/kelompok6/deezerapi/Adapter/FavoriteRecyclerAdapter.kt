package com.kelompok6.deezerapi.Adapter
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.kelompok6.deezerapi.Room.DataDao
import com.kelompok6.deezerapi.R
import com.kelompok6.deezerapi.Room.RoomDataBase
import com.kelompok6.deezerapi.databinding.FavoriteItemBinding
import com.kelompok6.deezerapi.models.album.favoriteMusic
import com.bumptech.glide.Glide


class FavoriteRecyclerAdapter(private val FavoriteMusicList:ArrayList<favoriteMusic>) : RecyclerView.Adapter<FavoriteRecyclerAdapter.MusicFavoriHolder>() {
        private lateinit var db : RoomDataBase
        private lateinit var adventDao: DataDao

        class MusicFavoriHolder (val binding : FavoriteItemBinding): RecyclerView.ViewHolder(binding.root) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicFavoriHolder {
            val binding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context))
            return MusicFavoriHolder(binding)

        }

        override fun getItemCount(): Int {
            return FavoriteMusicList.size
        }

        override fun onBindViewHolder(holder: MusicFavoriHolder, position: Int) {
            holder.binding.titleFavoriteMusic.text = FavoriteMusicList[position].title
            val minutes = FavoriteMusicList[position].duration / 60
            val seconds = FavoriteMusicList[position].duration % 60
            val durationText = String.format("%d:%02d", minutes, seconds)
            holder.binding.durationFavoriteMusic.text = "Durasi : " + durationText

            var mediaPlayer: MediaPlayer? = null
            var currentMedia: String? = null

            holder.binding.imgPlayMusic.setOnClickListener {
                val mediaList = FavoriteMusicList[position].preview

                if (mediaPlayer?.isPlaying == true && currentMedia == mediaList) {
                    mediaPlayer?.stop()
                    mediaPlayer?.reset()
                    currentMedia = null
                } else {
                    mediaPlayer?.stop()
                    mediaPlayer?.reset()
                    mediaPlayer?.release() // menghentikan lagu sebelumnya
                    mediaPlayer = MediaPlayer()
                    mediaPlayer?.setOnCompletionListener {
                        currentMedia = null
                        mediaPlayer = null
                    }
                    mediaPlayer?.setDataSource(mediaList)
                    mediaPlayer?.prepare()
                    mediaPlayer?.start()
                    currentMedia = mediaList
                }
            }

            Glide.with(holder.binding.imageView)
                .load("https://e-cdns-images.dzcdn.net/images/cover/" + FavoriteMusicList[position].md5_image + "/500x500-000000-80-0-0.jpg")
                .into(holder.binding.imageView)
            db =
                Room.databaseBuilder(holder.binding.root.context, RoomDataBase::class.java, "Music")
                    .allowMainThreadQueries()
                    .build()
            adventDao = db.dataDao()

            holder.binding.deleteFavorite.setOnClickListener {
                val favoriMusic = FavoriteMusicList[position]
                adventDao.delete(favoriMusic)
                Toast.makeText(holder.itemView.context, "Dihapus dari Favorite", Toast.LENGTH_SHORT)
                    .show()

                // Menghapus lagu dari favorite
                FavoriteMusicList.removeAt(position)
                notifyItemRemoved(position)
                holder.itemView.findNavController().navigate(R.id.favoriteFragment)
            }
        }
}