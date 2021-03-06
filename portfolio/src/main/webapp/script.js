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

google.charts.load('current', {'packages':['corechart', 'bar']});
google.charts.setOnLoadCallback(drawChart);

/** Creates a chart and adds it to the page. */
function drawChart() {
fetch('/videogame-data').then(response => response.json()).then((videoGameData) => {
  const data = new google.visualization.DataTable();
  data.addColumn('string', 'Game');
  data.addColumn('number', 'Userplay');
  Object.keys(videoGameData).forEach((game) => {
      data.addRow([game, videoGameData[game]]);
    });

  const options = {
    'title': 'Most popular video games in the U.S. 2019',
    vAxis: {
          'title': 'Games',
           },
    hAxis: {
        'format':'#%',
         minValue: 0
    }
  };

  const chart = new google.visualization.BarChart(document.getElementById('barchart_material'));
  chart.draw(data, options);
  });   
}

function getDataResponseUsingArrowFunctions(){
  var q = document.getElementById('limit');
  var url = "/data?limit=";
  fetch(url + q.value).then(response => response.json()).then((greet) => {
    const greetingsList = document.getElementById('greetings-container');
    greetingsList.innerHTML = '';
    console.log(greet);
    
    //Build the list of all the comments that have been left on the page.
    for (let key in greet) {
      console.log(key, greet[key]);
      greetingsList.appendChild(createListElement(greet[key].email, greet[key].comment));
    }
  });
}

// When selected by the user the button will generate a random link to watch any of the animes listed in the array
function randomAnime(){
  const animeList = ["https://www.crunchyroll.com/jojos-bizarre-adventure", "https://www.crunchyroll.com/yu-gi-oh-zexal", "https://www.crunchyroll.com/toradora"
    , "https://www.crunchyroll.com/fruits-basket", "https://www.crunchyroll.com/dragon-ball-super", "https://www.crunchyroll.com/my-hero-academia", "https://www.crunchyroll.com/sword-art-online", "https://www.netflix.com/title/80241960"];

  const animePick = animeList[Math.floor(Math.random() * animeList.length)];
  window.open(animePick);
}

function createListElement(text1, text2) {
  const liElement = document.createElement('li');
  liElement.innerText = text1 + " commented: " + text2;
  return liElement;
}
