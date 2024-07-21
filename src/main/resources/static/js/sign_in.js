const emailInput = document.getElementById('email');
const motDePasseInput = document.getElementById('pwd');
const checkBox = document.getElementById('check');
const form = document.getElementById('form');


let emailValide = false;
let motDePasseValide = false;
let checkBoxValide = false;


emailInput.addEventListener('input', () => {
  const email = emailInput.value;

  if (email.includes('@gmail.com') && email.length>("@gmail.com".length + 3)) {
    emailInput.style.border = '2px solid green ';
    emailValide = true;
  } else {
    emailInput.style.border = '2px solid red';
    emailValide = false;
  }
});

motDePasseInput.addEventListener('input', () => {
    const motDePasse = motDePasseInput.value;
  
    if ( motDePasse.length>4) {
      motDePasseInput.style.border = '2px solid green ';
      motDePasseValide = true;
    } else {
      motDePasseInput.style.border = '2px solid red';
      motDePasseValide = false;
    }
  });

  form.addEventListener('submit', (event) => {
    checkBoxValide = checkBox.checked
    if(!(checkBoxValide && motDePasseValide && emailValide)){
        event.preventDefault();
        let div = document.createElement("div")
        div.className = "error"
        div.textContent = "Verifier vos champs,les identifiants sont incorrects"
        form.insertBefore(div,form.firstChild)
    }
  })

