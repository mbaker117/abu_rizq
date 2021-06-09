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
package com.mbaker.abumazrouqdashboard.morpheus.domain;

public enum InventoryStatus {
    INSTOCK("In Stock"),
    OUTOFSTOCK("Out of Stock"), 
    LOWSTOCK("Low Stock");
 
    private String text;
 
    InventoryStatus(String text) {
        this.text = text;
    }
 
    public String getText() {
        return text;
    }
}