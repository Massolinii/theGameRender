import { Link } from "react-router-dom";

function LearnToWriteHome() {
  return (
    <div className="category-page-container text-white py-4 px-3 ">
      <tt>
        <h2 className="pt-2 pb-3">
          Want to learn how to write better prompts?
        </h2>
        <p>
          Still unsure on how to give life to the characters inside your mind?{" "}
          <br />
          Or how to make the interior of that room? Has that village design been
          bugging you for the last 2 hours?
        </p>
        <h5 className="py-3">
          Visit our{" "}
          <Link to="/how-to-write-a-prompt">
            "How to Write a Prompt for games"
          </Link>{" "}
          guide for basic prompt guidelines.
        </h5>
      </tt>
    </div>
  );
}

export default LearnToWriteHome;
