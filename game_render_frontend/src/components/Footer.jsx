import "../css/Footer.css";

function Footer() {
  return (
    <footer>
      <div className="text-center pb-3 pt-2 footer-background">
        <a className="" href="https://github.com/Massolinii/theGameRender">
          <p className="display-6 mb-0 footer-text">THE__GAME__RENDER</p>
        </a>{" "}
        <small className="text-white-50">
          2023. Made by Massimiliano Esposito -{" "}
          <a className="" href="https://github.com/Massolinii">
            GitHub
          </a>{" "}
          -{" "}
          <a className="" href="https://www.linkedin.com/in/massolini/">
            LinkedIn
          </a>
        </small>
      </div>
    </footer>
  );
}

export default Footer;
