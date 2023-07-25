import { Container } from "react-bootstrap";
import "../css/HowToWrite.css";
import elvishImage1 from "../css/images/elvish1.webp";
import elvishImage2 from "../css/images/elvish2.webp";
import elvishImage3 from "../css/images/elvish3.webp";
import elvishImage4 from "../css/images/elvish4.webp";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHouseChimney } from "@fortawesome/free-solid-svg-icons";

function HowToWrite() {
  return (
    <div className="category-page-container text-white py-5 px-3 full-screen">
      <Link className="go-back-home p-3" to={"/"}>
        <FontAwesomeIcon icon={faHouseChimney} /> Return Home
      </Link>
      <h1 className="pt-2 pb-3">Writing a Game Asset Prompt</h1>
      <p>
        Writing effective prompts on MidJourney for the development of game
        assets can be quite an engaging activity. Here are some key steps you
        can follow to write useful prompts:
      </p>
      <ul>
        <li>
          <strong>Understand Your Target Audience:</strong> First and foremost,
          it's crucial to understand who the game assets you're developing are
          for. Are they for a fantasy game, a racing game, a first-person
          shooter, or something completely different? Knowing your audience will
          help you develop prompts that are relevant and useful.
        </li>
        <br />
        <li>
          <strong>Establish the Environment and Style:</strong> Every game has
          its unique style and environment. It can be cartoonish, realistic,
          dark, bright, etc. When writing your prompt, make sure to specify the
          style and environment in which the asset will be used.
        </li>
        <pre className="code-block">
          <code>
            {" "}
            <b>Design a Elvish Townhall, in a forest </b>. . . . .
          </code>
        </pre>
        <Container className="d-flex justify-content-center p-0 m-0 mb-4">
          <img
            src={elvishImage1}
            alt="Elvish Townhall"
            className="tutorial-elvish-image"
          />
        </Container>
        <li>
          <strong>Provide a Clear and Concise Description:</strong> A good
          prompt should be easy to understand but detailed enough to give a good
          idea of what's needed. Try to be as specific as possible regarding
          sizes, colors, materials, and other relevant details.
        </li>
        <pre className="code-block">
          <code>
            <span className="text-muted">
              Design a Elvish Townhall, in a forest,{" "}
            </span>
            <b>
              with an ancient oak tree erupting from the center, a lot of people
              and wooden forniture,{" "}
            </b>{" "}
            <span className="text-muted">. . . . .</span>{" "}
          </code>
        </pre>
        <Container className="d-flex justify-content-center p-0 m-0 mb-4">
          <img
            src={elvishImage2}
            alt="Elvish Townhall"
            className="tutorial-elvish-image"
          />
        </Container>
        <li>
          <strong>Use Appropriate Language:</strong> The language you use in
          your prompt should be appropriate for your target audience. If you're
          writing for professional game developers, you can use technical
          terminology. If your audience is broader, it might be better to use
          simpler, more accessible language.
        </li>
        <h5 className="pt-4">Simpler language</h5>
        <pre className="code-block">
          <code>
            <span className="text-muted">
              Design a Elvish Townhall, in a forest, with an ancient oak tree
              erupting from the center, a lot of people and wooden forniture,{" "}
            </span>
            <b>
              the Townhall showcase magical advanced engineering and a sense of
              tranquility and peace.{" "}
            </b>
            <span className="text-muted">. . . . .</span>{" "}
          </code>
        </pre>
        <h5>Technical language</h5>
        <pre className="code-block">
          <code>
            <span className="text-muted">
              Design a Elvish Townhall, in a forest, with an ancient oak tree
              erupting from the center, a lot of people and wooden forniture,{" "}
            </span>
            <b>magic steampunk, tranquility, muted colors, hazy</b>
            <span className="text-muted">. . . . .</span>{" "}
          </code>
        </pre>
        <Container className="d-flex justify-content-center p-0 m-0 mb-4">
          <img
            src={elvishImage3}
            alt="Elvish Townhall"
            className="tutorial-elvish-image"
          />
        </Container>
        <li>
          <strong>Include Visual References:</strong> If possible, include
          visual references in your prompt. These can be images, sketches, or
          links to videos that show what you have in mind. Visual references can
          be very helpful in clarifying your intent.
        </li>
        <pre className="code-block mb-2">
          <code>
            <span className="text-muted">
              Design a Elvish Townhall, in a forest, with an ancient oak tree
              erupting from the center, a lot of people and wooden forniture,
              the townhall showcase magical advanced engineering and a sense of
              tranquility and peace{" "}
            </span>
            <b>in the style of krenz cushart</b>
          </code>
        </pre>
        <Container className="d-flex justify-content-center p-0 m-0 mb-4">
          <img
            src={elvishImage4}
            alt="Elvish Townhall"
            className="tutorial-elvish-image"
          />
        </Container>

        <li>
          <strong>Review and Refine:</strong> Finally, don't be afraid to review
          and refine your prompt. Effective writing is a process, and you may
          need to make some changes before you get the desired result.
        </li>
        <pre className="code-block">
          <code>
            <span className="text-muted text-decoration-line-through">
              Design a Elvish Townhall, in a forest, with an ancient oak tree
              erupting from the center, a lot of people and wooden forniture,
              the townhall showcase magical advanced engineering and a sense of
              tranquility and peace in the style of krenz cushart
            </span>{" "}
            <b>Make a sandwich that swears to people, a lot.</b>
          </code>
        </pre>
      </ul>
      <p>
        Here's an example of a well-written prompt for a game asset: "For a
        cartoonish-style fantasy adventure game, a series of assets for an elven
        village. The buildings should be made of wood and leaves, with tall and
        curvilinear architecture typical of elven structures. The sizes of the
        buildings should vary, with smaller houses for citizens and larger,
        ornate structures for figures of power. The predominant color should be
        green, with touches of brown and gold."
      </p>
      <p>
        Remember, the goal of a prompt is to clearly communicate what you want.
        The clearer and more precise you are, the easiest is to generate what
        you had in mind!
      </p>
      <Link className="go-back-home px-3" to={"/"}>
        <FontAwesomeIcon icon={faHouseChimney} /> Return Home
      </Link>
    </div>
  );
}

export default HowToWrite;
