const voteBtn = document.querySelector("#vote-btn");
const candidateIdInput = document.querySelector("#candidateIdInput");
const form = document.querySelector("#form");

function selectCandidate(event, id) {
  const card = event.currentTarget;
  removeSelect();
  card.classList.add("selected");
  candidateIdInput.value = id;

}

function removeSelect() {
  const cards = document.querySelectorAll(".candidate-card");
  for (const card of cards) {
    card.classList.remove("selected");
  }
}

function handleSubmit(event) {
  event.preventDefault();
  console.log(candidateIdInput);
  if (candidateIdInput.value) {
    form.submit()
  }

}