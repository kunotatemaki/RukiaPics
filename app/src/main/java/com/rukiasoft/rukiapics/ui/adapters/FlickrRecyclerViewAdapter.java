/*
 * Copyright (C) 2015 Antonio Leiva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rukiasoft.rukiapics.ui.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.rukiasoft.rukiapics.R;
import com.rukiasoft.rukiapics.model.PicturePojo;
import com.rukiasoft.rukiapics.utilities.RukiaConstants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.PictureViewHolder>
        implements View.OnClickListener {

    private final List<PicturePojo> mItems;
    private OnCardClickListener onCardClickListener;
    private final Context mContext;

    public FlickrRecyclerViewAdapter(Context context, List<PicturePojo> items) {
        this.mItems = new ArrayList<>(items);
        this.mContext = context;
    }

    public void setOnCardClickListener(OnCardClickListener onCardClickListener) {
        this.onCardClickListener = onCardClickListener;
    }

    public int getPositionOfItem(PicturePojo item){
        return mItems.indexOf(item);
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_recycler_item, parent, false);
        PictureViewHolder pictureViewHolder = new PictureViewHolder(v);
        pictureViewHolder.cardView.setOnClickListener(this);

        return pictureViewHolder;
    }


    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        PicturePojo item = mItems.get(position);
        holder.bindPicture(mContext, item);
        holder.itemView.setTag(item);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position){
        return mItems.get(position).hashCode();
    }

    @Override
    public void onClick(final View v) {

        // Give some time to the ripple to finish the effect
        if (onCardClickListener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onCardClickListener.onCardClick(v, (PicturePojo) v.getTag());
                }
            }, 200);
        }
    }


    static class PictureViewHolder extends RecyclerView.ViewHolder {
        // region binding views
        @BindView(R.id.pic_item)
        ImageView movieThumbnail;
        @BindView(R.id.cardview_item)
        FrameLayout cardView;
        //endregion

        Unbinder unbinder;

        PictureViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);

        }

        void bindPicture(Context mContext, PicturePojo item) {
            Glide.with(mContext)
                    .load(item.getUrlM())
                    .signature(new MediaStoreSignature(RukiaConstants.MIME_TYPE_PICTURE, item.getTimestamp(), 0))
                    .into(movieThumbnail);
        }

    }

    public interface OnCardClickListener {
        void onCardClick(View view, PicturePojo pictureItem);
    }


}
