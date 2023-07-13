import { Col, Row } from "react-bootstrap";
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
          via Midjourney to use as a reference for various artists.
        </p>
        <p className="quest-text px-3 pb-4">
          Q : Our mission?
          <br />A : to help you create epic game assets.
        </p>
        <p className="quest-text px-3 m-0">
          You can be a 3D blender designer, a 2D isometric artist or a
          Dungeon&Dragons master. Let ideas flow!
        </p>
        <br />
      </div>
      <div className="spacer spacer-two"></div>
    </div>
  );
}

export default Quest;
