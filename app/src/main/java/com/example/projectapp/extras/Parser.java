package com.example.projectapp.extras;

import com.example.projectapp.AnimationModel;
import com.example.projectapp.ComponentModel;
import com.example.projectapp.PlaylistModel;
import com.example.projectapp.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//This class is used to parse Json data into java object retrieved from server
public class Parser {

	//parsetoobject method takes json string and convert it ibto java object and returns array of PlaylistModel
	//this methods throws JSONException
	public static PlaylistModel[] parsetoobject(String json) throws JSONException {

		JSONArray array=new JSONArray(json);

		//create PlaylistModel Array
		PlaylistModel[] playlists =new PlaylistModel[array.length()];

		for(int k=0;k<array.length();k++) {
			PlaylistModel list = null;

			JSONObject obj=array.getJSONObject(k);
			Integer pid = obj.getInt("id");
			String name = obj.getString("name");
			Integer height = obj.getInt("height");
			Integer width = obj.getInt("width");
			JSONArray slides = obj.getJSONArray("slides");

			//create List of SlideModel
			List<SlideModel> slideModels = new ArrayList<SlideModel>();
			for (int i = 0, length = slides.length(); i < length; i++) {
				Integer audio = null;
				JSONObject slide = slides.getJSONObject(i);
				Integer bgimage = null;
				Integer duration = null;
				Integer next = null;
				Boolean animate = null;
				String sname = null;
				String bgcolor = null;
				AnimationModel enter_animation=null;
				AnimationModel exit_animation=null;

				Integer sid = slide.getInt("id");
				if (slide.has("bgimage"))
					bgimage = slide.getInt("bgimage");
				if (slide.has("duration"))
					duration = slide.getInt("duration");
				if (slide.has("next"))
					next = slide.getInt("next");
				if (slide.has("audio"))
					audio = slide.getInt("audio");
				if (slide.has("animate"))
					animate = slide.getBoolean("animate");
				if (slide.has("name"))
					sname = slide.getString("name");
				if (slide.has("bgcolor"))
					bgcolor = slide.getString("bgcolor");
				if (slide.has("enter_animation"))
				{
					JSONObject enter = slide.getJSONObject("enter_animation");
					String atype = null;
					Integer delay = null, aduration = null;
					if (enter.has("type"))
						atype = enter.getString("type");
					if (enter.has("delay"))
						delay = enter.getInt("delay");
					if (enter.has("duration"))
						aduration = enter.getInt("duration");
					enter_animation = new AnimationModel(atype, delay, aduration);
				}
				if (slide.has("exit_animation"))
				{
					JSONObject enter = slide.getJSONObject("exit_animation");
					String atype = null;
					Integer delay = null, aduration = null;
					if (enter.has("type"))
						atype = enter.getString("type");
					if (enter.has("delay"))
						delay = enter.getInt("delay");
					if (enter.has("duration"))
						aduration = enter.getInt("duration");
					exit_animation = new AnimationModel(atype, delay, aduration);
				}

				//Create List of ComponentModel
				List<ComponentModel> componentModels = new ArrayList<ComponentModel>();
				if (slide.has("components")) {
					JSONArray components = slide.getJSONArray("components");
					for (int j = 0, length2 = components.length(); j < length2; j++) {
						JSONObject component = components.getJSONObject(j);
						Integer cid;
						String type;
						Integer left = null;
						Integer top = null;
						Double cheight = null;
						Double cwidth = null;
						String uri = null;
						String shadow = null;
						Integer scaleX = null;
						Integer scaleY = null;
						Integer z_index = null;
						Integer angle = null;
						Double opacity = null;
						String onClick = null;
						Boolean is_animate = null;

						cid = component.getInt("id");
						type = component.getString("type");
						if (component.has("left"))
							left = component.getInt("left");
						if (component.has("top"))
							top = component.getInt("top");
						if (component.has("height"))
							cheight = component.getDouble("height");
						if (component.has("width"))
							cwidth = component.getDouble("width");
						if (component.has("uri"))
							uri = component.getString("uri");
						if (component.has("shadow"))
							shadow = component.getString("shadow");
						if (component.has("scaleX"))
							scaleX = component.getInt("scaleX");
						if (component.has("scaleY"))
							scaleY = component.getInt("scaleY");
						if (component.has("z_index"))
							z_index = component.getInt("z_index");
						if (component.has("angle"))
							angle = component.getInt("angle");
						if (component.has("opacity"))
							opacity = component.getDouble("opacity");
						if (component.has("onClick"))
							onClick = component.getString("onClick");
						if (component.has("is_animate"))
							is_animate = component.getBoolean("is_animate");
						AnimationModel enteranimation = null, exitanimation = null;
						if (component.has("enter_animation")) {
							JSONObject enter = component.getJSONObject("enter_animation");
							String atype = null;
							Integer delay = null, aduration = null;
							if (enter.has("type"))
								atype = enter.getString("type");
							if (enter.has("delay"))
								delay = enter.getInt("delay");
							if (enter.has("duration"))
								aduration = enter.getInt("duration");
							enteranimation = new AnimationModel(atype, delay, aduration);
						}

						if (component.has("exit_animation")) {
							JSONObject exit = component.getJSONObject("exit_animation");
							String atype = null;
							Integer delay = null, aduration = null;

							if (exit.has("type"))
								atype = exit.getString("type");
							if (exit.has("delay"))
								delay = exit.getInt("delay");
							if (exit.has("duration"))
								aduration = exit.getInt("duration");

							exitanimation = new AnimationModel(atype, delay, aduration);
						}
						//add New ComponentModel in List of COmponentModel
						componentModels.add(new ComponentModel(cid, type, left, top, cwidth, cheight, uri, shadow, scaleX, scaleY, z_index, angle, opacity, onClick, is_animate, enteranimation, exitanimation));
					}
				}
				//Create Object of SlideModel from data retrieved from Json string
				SlideModel model = new SlideModel(sid, bgimage, duration, next, audio, animate, name, bgcolor,enter_animation,exit_animation);
				model.setComponents(componentModels);
				slideModels.add(model);
			}
			//create new PlaylistModel object from data retrived from json string
			list = new PlaylistModel(pid, name, height, width, slideModels);

			//add PlaylistModel in to array of PlaylistModel
			playlists[k]=list;
		}
		//retrun Playlist
		return playlists;
	}
}
