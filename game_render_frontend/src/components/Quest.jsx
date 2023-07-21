import "../css/Quest.css";

function Quest() {
  return (
    <div>
      <div className="spacer spacer-one"></div>
      <div className="quest-bg ps-4">
        <h2 className="quest-title">OUR_QUEST</h2>
        <p className="quest-text px-3 pb-4">
          Welcome to THE_GAME_RENDER, your one-stop digital tavern for all
          things game design! We're on a quest, not for the mythical Excalibur
          or a Dragon's hoard, but to provide an arsenal of AI-generated images
          to use as a reference for various artists made with{" "}
          <span className="magic"> Midjourney</span> .
          <br />
          All the image in this site were generated using a text prompt
          describing the image. Feel free to copy and modify the prompts you
          find here as they better suits you and create{" "}
          <span className="color-red">sprites</span>,{" "}
          <span className="color-orange">references</span>,{" "}
          <span className="color-yellow">character icons</span>,{" "}
          <span className="color-green">monster-pedia sketches</span>,{" "}
          <span className="color-blue">backgrounds</span>,{" "}
          <span className="color-purple">textures</span>, and much, much{" "}
          <span className="color-pink">more</span>.
        </p>
        <p className="quest-text px-3 pb-4">
          Q : Our mission?
          <br />A : to help you create epic game assets.
        </p>
        <p className="quest-text px-3 m-0">
          You can be a 3D Blender designer, a 2D isometric pixel artist or a
          Dungeon&Dragons master. Let ideas flow!
        </p>
        <br />
      </div>
      <div className="spacer spacer-two"></div>
    </div>
  );
}

export default Quest;
