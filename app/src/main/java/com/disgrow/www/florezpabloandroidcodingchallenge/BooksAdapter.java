package com.disgrow.www.florezpabloandroidcodingchallenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {

    private List<Books> booksList;
    private int widthScreen;
    private int heightScreen;
    private Typeface font;
    private Context c;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llTotalRow;
        private RelativeLayout rlImageBook;
        private ImageView imgBook;
        private LinearLayout llTitleAndAuthor;
        private TextView tvTitle;
        private TextView tvAuthor;


        public MyViewHolder(View view) {
            super(view);

            llTotalRow = view.findViewById(R.id.llTotalRow);
            rlImageBook = view.findViewById(R.id.rlImageBook);
            imgBook = view.findViewById(R.id.imgBook);
            llTitleAndAuthor = view.findViewById(R.id.llTitleAndAuthor);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvAuthor = view.findViewById(R.id.tvAuthor);

            Methods.setParamsView(rlImageBook,w(30),0);
            Methods.setParamsView(llTitleAndAuthor,w(70),0);

            Methods.setTextViewProperties(tvTitle, w(4.5f), c.getResources().getColor(R.color.gray), font, "",w(3),h(1),w(3),h(0.5f));
            Methods.setTextViewProperties(tvAuthor, w(3.5f), c.getResources().getColor(R.color.black), font, "",w(3),h(0.5f),w(3),h(1));

            Methods.setMargenes(imgBook,0,h(1),0,h(1));
        }
    }

    public BooksAdapter(List<Books> meowList, int widthScreen, int heightScreen, Typeface font, Context c) {
        this.booksList = meowList;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
        this.font = font;
        this.c = c;
    }

    @NonNull
    @Override
    public BooksAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {

        final Books books = booksList.get(position);
        final String unknown = c.getString(R.string.unknown);
        final String author = c.getString(R.string.author);

        myViewHolder.tvTitle.setText(books.getTitle());

        if(books.getAuthor() == null){
            myViewHolder.tvAuthor.setText(author+" "+unknown);
        }else{
            myViewHolder.tvAuthor.setText(author+" "+books.getAuthor());
        }

        getImageFromUrl(c, books.getImageURL(), myViewHolder.imgBook);

        myViewHolder.llTotalRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(books.getAuthor() == null){
                    showMessage(books.getTitle());
                }else{
                    showMessage(books.getTitle()+" By "+books.getAuthor());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public int w(float percent) {
        return (int) (widthScreen * (percent / 100));
    }

    public int h(float percent) {
        return (int) (heightScreen * (percent / 100));
    }


    public void getImageFromUrl(final Context c, String url, final ImageView imgView) {
        Glide.with(c)
                .load(url)
                .asBitmap()
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .format(DecodeFormat.PREFER_ARGB_8888)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bm, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap mBitmap = resizeBitmap(bm,w(25));
                        imgView.setImageBitmap(mBitmap);
                    }
                });
    }

    private Bitmap resizeBitmap(Bitmap bmap, int tam) {

        double percentaje = 0;
        double div = 100;
        double width = bmap.getWidth();
        double height = bmap.getHeight();

        if (bmap.getWidth() > bmap.getHeight()) {
            percentaje = (tam * 100) / width;
        } else if (bmap.getHeight() >= bmap.getWidth()) {
            percentaje = (tam * 100) / height;
        }

        percentaje = percentaje / div;
        Bitmap bm = Methods.resizeImage(bmap, (float) (bmap.getWidth() * percentaje), (float) (bmap.getHeight() * percentaje));
        return bm;

    }

    public void showMessage(String str) {
        Methods.myCustomToast(c,
                str,
                c.getResources().getDrawable(R.drawable.toast_background),
                c.getResources().getColor(R.color.white),
                font,
                w(5),
                w(3),
                w(3));
    }

}
