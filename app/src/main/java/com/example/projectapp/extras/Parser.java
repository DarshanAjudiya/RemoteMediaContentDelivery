package com.example.projectapp.extras;

import com.example.projectapp.*;

import org.json.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static PlaylistModel parsetoobject(String json) throws JSONException {
        PlaylistModel list = null;
        JSONObject obj = new JSONObject(json);
        Integer pid = obj.getInt("id");
        String name = obj.getString("name");
        Integer height = obj.getInt("height");
        Integer width = obj.getInt("width");
        JSONArray slides = obj.getJSONArray("slides");
        List<SlideModel> slideModels = new ArrayList<SlideModel>();
        for (int i = 0, length = slides.length(); i < length; i++) {
            Integer audio;
            JSONObject slide = slides.getJSONObject(i);
            Integer bgimage;
            Integer duration;
            Integer next;
            Integer animduration;
            Boolean animate;
            String sname;
            String bgcolor;
            String anaimation;

            Integer sid = slide.getInt("id");
            if (slide.has("bgimage"))
                bgimage = slide.getInt("bgimage");
            if (slide.has("duration"))
                duration = slide.getInt("duration");
            if (slide.has("next"))
                next = slide.getInt("next");
            if (slide.has("animduration"))
                animduration = slide.getInt("animduration");
            if (slide.has("audio"))
                audio = slide.getInt("audio");
            if (slide.has("animate"))
                animate = slide.getBoolean("animate");
            if (slide.has("name"))
                sname = slide.getString("name");
            if (slide.has("bgcolor"))
                bgcolor = slide.getString("bgcolor");
            if (slide.has("animation"))
                anaimation = slide.getString("animation");
            if(slide.has("components")) {
                JSONArray components = slide.getJSONArray("components");
                for (int j = 0, length2 = components.length(); j < length2; j++) {
                    JSONObject component = components.getJSONObject(j);
                    Integer cid;
                    String type;
                    Integer left;
                    Integer top;
                    Double cheight;
                    Double cwidth;
                    String uri;
                    String shadow;
                    Integer scaleX;
                    Integer scaleY;
                    Integer z_index;
                    Integer angle;
                    Double opacity;
                    String onClick;
                    Boolean is_animate;

                    cid = component.getInt("id");
                    type = component.getString("type");
                    left = component.getInt("left");
                    top = component.getInt("top");
                    cheight = component.getDouble("height");
                    cwidth = component.getDouble("width");
                    uri = component.getString("uri");
                    shadow = component.getString("shadow");
                    scaleX = component.getInt("scaleX");
                    scaleY = component.getInt("scaleY");
                    z_index = component.getInt("z_index");
                    angle = component.getInt("angle");
                    opacity = component.getDouble("opacity");
                    onClick = component.getString("onClick");
                    is_animate = component.getBoolean("is_animate");
                    JSONObject enter = component.getJSONObject("enter_animation");
                    AnimationModel enteranimation;
                    if (enter != null) {

                    }

                }
            }
        }


        return list;
    }
}
