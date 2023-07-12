import { NavLink } from "react-router-dom";
import "../css/HomeCategories.css";

function HomeCategories() {
    return (
        <div>
        <div className="environment-bg category-name">
            <a to="/category/1"
            ><p className="title">ENVIRONMENT</p>
            </a>
        </div>
        <div className="environment-bg category-name">
            <a to="/category/1"
            ><p className="title">ENVIRONMENT</p>
            </a>
        </div>
        </div>
    );
}

export default HomeCategories;