package com.example.user.georgaphywar;

import java.util.Random;

public class Checking {
    String[]  names =
            {       "Russia",
                    "Ukraine",
                    "France ",
                    "Spain",
                    "Sweden",
                    "Norway",
                    "Germany",
                    "Finland",
                    "Poland",
                    "Italy",
                    "UnitedKingdom",
                    "Romania",
                    "Belarus",
                    "Kazakhstan",
                    "Greece",
                    "Bulgaria",
                    "Iceland",
                    "Hungary",
                    "Portugal",
                    "Serbia",
                    "Ireland",
                    "Austria",
                    "CzechRepublic",
                    "Georgia",
                    "Lithuania",
                    "Latvia",
                    "Croatia",
                    "BosniaandHerzegovina",
                    "Slovakia",
                    "Estonia",
                    "Denmark",
                    "Netherlands",
                    "Switzerland",
                    "Moldova",
                    "Belgium",
                    "Albania",
                    "Macedonia",
                    "Turkey",
                    "Slovenia",
                    "Montenegro",
                    "Azerbaijan"};
    String[]  colors =
            {       "fffba7",
                    "cec1df",
                    "fecd67",
                    "cb9ac7",
                    "b3b1d8",
                    "f1b9d6",
                    "fce267",
                    "bfd3a0",
                    "e3fbad",
                    "e2e2fc",
                    "f69899",
                    "f5888a",
                    "fecd67",
                    "f1fbfc",
                    "a8cf58",
                    "c78e57",
                    "87d1d4",
                    "fdffab",
                    "fdffac",
                    "c3e2dd",
                    "a8cf58",
                    "96bb49",
                    "fbcb9a",
                    "f7e5cf",
                    "c991c5",
                    "69bd44",
                    "f0ef9d",
                    "cb9765",
                    "68c2c5",
                    "c99c72",
                    "ebcfcc",
                    "fbf49c",
                    "f6eb15",
                    "faf7c2",
                    "5ba33b",
                    "b3b1d8",
                    "f6eb15",
                    "c7f7bd",
                    "e8bffb",
                    "f7868a",
                    "cfd8ff"};
        int[] coordinateX = {};
    String color;
    Random rand = new Random();
    int i;
    public String getRandom(){
            i = rand.nextInt(41);
            return names[i];
    }
        public String getColor(){
                return colors[i];
        }
    public boolean test(String color){
        if(colors[i] == color) return true;
        else return false;
    }
}
