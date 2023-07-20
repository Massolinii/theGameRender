import "../css/HowToWrite.css";

function HowToWrite() {
  return (
    <div className="category-page-container text-white py-5 px-3 full-screen">
      <tt>
        <h1 className="pt-5 pb-3">Writing a Game Asset Prompt</h1>
        <p>
          Writing effective prompts on MidJourney for the development of game
          assets can be quite an engaging activity. Here are some key steps you
          can follow to write useful prompts:
        </p>
        <ul>
          <li>
            <strong>Understand Your Target Audience:</strong> First and
            foremost, it's crucial to understand who the game assets you're
            developing are for. Are they for a fantasy game, a racing game, a
            first-person shooter, or something completely different? Knowing
            your audience will help you develop prompts that are relevant and
            useful.
          </li>
          <br />
          <li>
            <strong>Establish the Environment and Style:</strong> Every game has
            its unique style and environment. It can be cartoonish, realistic,
            dark, bright, etc. When writing your prompt, make sure to specify
            the style and environment in which the asset will be used.
          </li>
          <pre className="code-block">
            <code>
              Design a Elvish Townhall, in a forest{" "}
              <span className="text-muted">. . . . .</span>{" "}
            </code>
          </pre>
          <li>
            <strong>Provide a Clear and Concise Description:</strong> A good
            prompt should be easy to understand but detailed enough to give a
            good idea of what's needed. Try to be as specific as possible
            regarding sizes, colors, materials, and other relevant details.
          </li>
          <pre className="code-block">
            <code>
              <span className="text-muted">
                Design a Elvish Townhall, in a forest,{" "}
              </span>
              with an ancient oak tree erupting from the center, a lot of people
              and wooden forniture,{" "}
              <span className="text-muted">. . . . .</span>{" "}
            </code>
          </pre>
          <li>
            <strong>Use Appropriate Language:</strong> The language you use in
            your prompt should be appropriate for your target audience. If
            you're writing for professional game developers, you can use
            technical terminology. If your audience is broader, it might be
            better to use simpler, more accessible language.
          </li>
          <h5 className="pt-4">Simpler language</h5>
          <pre className="code-block">
            <code>
              <span className="text-muted">
                Design a Elvish Townhall, in a forest, with an ancient oak tree
                erupting from the center, a lot of people and wooden forniture,{" "}
              </span>
              the Townhall showcase magical advanced engineering and a sense of
              tranquility and peace.
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
              magic steampunk, tranquility, muted colors, hazy
              <span className="text-muted">. . . . .</span>{" "}
            </code>
          </pre>
          <li>
            <strong>Include Visual References:</strong> If possible, include
            visual references in your prompt. These can be images, sketches, or
            links to videos that show what you have in mind. Visual references
            can be very helpful in clarifying your intent.
          </li>
          <pre className="code-block">
            <code>
              <span className="text-muted">
                Design a Elvish Townhall, in a forest, with an ancient oak tree
                erupting from the center, a lot of people and wooden forniture,
                the townhall showcase magical advanced engineering and a sense
                of tranquility and peace{" "}
              </span>
              in the style of krenz cushart
            </code>
          </pre>

          <li>
            <strong>Review and Refine:</strong> Finally, don't be afraid to
            review and refine your prompt. Effective writing is a process, and
            you may need to make some changes before you get the desired result.
          </li>
          <pre className="code-block">
            <code>
              <span className="text-muted text-decoration-line-through">
                Design a Elvish Townhall, in a forest, with an ancient oak tree
                erupting from the center, a lot of people and wooden forniture,
                the townhall showcase magical advanced engineering and a sense
                of tranquility and peace in the style of krenz cushart
              </span>{" "}
              Make a sandwich that swears to people, a lot.
            </code>
          </pre>
        </ul>
        <p>
          Here's an example of a well-written prompt for a game asset: "For a
          cartoonish-style fantasy adventure game, a series of assets for an
          elven village. The buildings should be made of wood and leaves, with
          tall and curvilinear architecture typical of elven structures. The
          sizes of the buildings should vary, with smaller houses for citizens
          and larger, ornate structures for figures of power. The predominant
          color should be green, with touches of brown and gold."
        </p>
        <p>
          Remember, the goal of a prompt is to clearly communicate what you
          want. The clearer and more precise you are, the easiest is to generate
          what you had in mind!
        </p>
      </tt>
    </div>
  );
}

export default HowToWrite;
