//Chiamata su API
const addBtn = document.getElementById('add');
console.log(addBtn);
addBtn.addEventListener('click', showPizzas);

//Importa pizze
function showPizzas()
{
    //console.log('bottone linkato ad html')
    axios.get('http://localhost:8080/api/pizze')
        .then
        (
            function (response) //Mi richiamo tutte le pizze registrate nel mio DB
            {
                for(let i=0; i<response.data.length; i++)
                {
                    pizza = response.data[i];
                    console.log(pizza);

                    createCard(pizza);
                }
                
            }
        )
        .catch(function(err)
        {
            console.log(err);
        });
}

function createCard(pizza) {
    // dall'oggetto user che arriva dall'api estraggo nome, cognome e url dell'immagine
    const name = pizza.nome;
    const description = pizza.descrizione;
    const picture = pizza.immagini.content;
    console.log(name, description, picture);

    // creo gli elementi del dom
    const col = createDomElement('div', 'col-4');
    const card = createDomElement('div', 'card');
    const img = createDomElement('img', 'card-img-top');
    img.src = picture;
    const cardBody = createDomElement('div', 'card-body');
    const h5 = createDomElement('h5', 'card-title');
    h5.innerText = name;
    cardBody.appendChild(h5);
    card.appendChild(img);
    card.appendChild(cardBody);
    col.appendChild(card);
    // aggiungo la card al dom
    document.getElementById('gallery').appendChild(col);
  }

  function createDomElement(type, className) {
    const el = document.createElement(type);
    el.className = className;
    return el;
  }