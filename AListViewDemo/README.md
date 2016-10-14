# A ListView Demo

### Overview

Demo of ListView with custom and native adapter for entity class(Fruit in this case).

### Table of contents

 1. ``class Fruit`` defines Entity Class
 - ``class FruitAdapter extends ArrayAdapter<Fruit>`` defines custom adapter
 - FruitAdapter constructor
 - listView.setAdapter
 - ``@Override getView``
 - ``getItem``
 - ``LayoutInflater.from(getContext()).inflate(id, null)`` : Obtains the LayoutInflater from the given context.Instantiates a layout XML file into its corresponding View objects
 - [LayoutInflater](https://developer.android.com/reference/android/view/LayoutInflater.html)