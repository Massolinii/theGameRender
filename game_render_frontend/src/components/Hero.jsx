function Hero() {
  const letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  let interval = null;

  document.querySelector("h1").onmouseover = (event) => {
    let iteration = 0;

    clearInterval(interval);

    interval = setInterval(() => {
      event.target.innerText = event.target.innerText
        .split("")
        .map((letter, index) => {
          if (index < iteration) {
            return event.target.dataset.value[index];
          }

          return letters[Math.floor(Math.random() * 26)];
        })
        .join("");

      if (iteration >= event.target.dataset.value.length) {
        clearInterval(interval);
      }

      iteration += 1 / 3;
    }, 30);
  };

  return (
    <div className="hero-section" id="tiles">
      <img
        src={process.env.PUBLIC_URL + "/logo-transparent-upscaled.png"}
        alt="Logo"
        className="heroLogo"
      />
      <h1 data-value="THEGAMERENDER" id="thegamerender">
        THE GAME RENDER
      </h1>
    </div>
  );
}

export default Hero;
