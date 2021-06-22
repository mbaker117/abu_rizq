/*
   Copyright 2009-2021 PrimeTek.

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.mbaker.abumazrouqdashboard.morpheus.view;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import javax.inject.Named;

import org.springframework.web.context.annotation.SessionScope;

import javax.enterprise.context.SessionScoped;

@Named
@SessionScoped
@SessionScope
public class GuestPreferences implements Serializable {
        
    private String theme = "green";

    private String inputStyle = "outlined";
    
    private boolean overlayMenu = false;
    
    private boolean orientationRTL;

    private List<Theme> themes;

    @PostConstruct
    public void init() {
        themes = new ArrayList<>();
        themes.add(new Theme("Blue", "blue", "#6ec5ff"));
        themes.add(new Theme("Green", "green", "#61d42d"));
        themes.add(new Theme("Orange", "orange", "#fbc948"));
        themes.add(new Theme("Purple", "purple", "#7B6EFF"));
        themes.add(new Theme("Cyan", "cyan", "#12caaf"));
        themes.add(new Theme("Deep-Orange", "deeporange", "#FA8863"));
        themes.add(new Theme("Blue-Grey", "bluegrey", "#7A929E"));
        themes.add(new Theme("Indigo", "indigo", "#5B6BBF"));
        themes.add(new Theme("Lime", "lime", "#DBE955"));
        themes.add(new Theme("Pink", "pink", "#F1719C"));
    }

    public String getTheme() {
		return theme;
	}
    
	public void setTheme(String theme) {
		this.theme = theme;
	}

    public String getInputStyleClass() {
        return this.inputStyle.equals("filled") ? "ui-input-filled" : "";
    }

    public String getInputStyle() {
        return inputStyle;
    }

    public void setInputStyle(String inputStyle) {
        this.inputStyle = inputStyle;
    }
        
    public boolean isOverlayMenu() {
        return this.overlayMenu;
    }
    
    public void setOverlayMenu(boolean value) {
        this.overlayMenu = value;
    }

    public boolean isOrientationRTL() {
        return orientationRTL;
    }

    public void setOrientationRTL(boolean orientationRTL) {
        this.orientationRTL = orientationRTL;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public class Theme {

        private String name;

        private String file;

        private String color;

        public Theme() {}

        public Theme(String name, String file, String color) {
            this.name = name;
            this.file = file;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
