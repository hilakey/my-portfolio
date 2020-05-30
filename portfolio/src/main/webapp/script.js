// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


// When selected by the user the button will generate a random link to watch any of the animes listed in the array

function randomAnime(){
    const animeList = ["https://www.crunchyroll.com/jojos-bizarre-adventure", "https://www.crunchyroll.com/yu-gi-oh-zexal", "https://www.crunchyroll.com/toradora"
    , "https://www.crunchyroll.com/fruits-basket", "https://www.crunchyroll.com/dragon-ball-super", "https://www.crunchyroll.com/my-hero-academia", "https://www.crunchyroll.com/sword-art-online", "https://www.netflix.com/title/80241960"];

    const animePick = animeList[Math.floor(Math.random() * animeList.length)];
    window.open(animePick);



}






