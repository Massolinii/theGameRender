import React, { useEffect, useState, useContext } from "react";
import { fetchUserFavorites } from "../api";
import { AuthContext } from "../AuthContext";
import copy from "clipboard-copy";
import ImageCard from "./ImageCard";
import { Link } from "react-router-dom";
import { faHouseChimney } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

function FavoritesPage() {
  const [favorites, setFavorites] = useState([]);
  const { user } = useContext(AuthContext);
  const [selectedImages, setSelectedImages] = useState([]);
  const [copiedImageId, setCopiedImageId] = useState(null);

  const handleImageClick = (id) => {
    if (selectedImages.includes(id)) {
      setSelectedImages(selectedImages.filter((imageId) => imageId !== id));
    } else {
      setSelectedImages([...selectedImages, id]);
    }
  };

  const handleCopyClick = (text, id) => {
    copy(text);
    setCopiedImageId(id);
    setTimeout(() => setCopiedImageId(null), 2500);
  };

  useEffect(() => {
    if (user && user.username) {
      fetchUserFavorites(user.username).then(setFavorites).catch(console.error);
    }
  }, [user]);

  return (
    <tt>
      <div className="category-page-container pt-5 full-screen">
        <div>
          <h1 className="p-5 text-white">Your Favorites</h1>
        </div>

        <div className="text-white pt-2">
          <Link className="go-back-home p-3" to={"/"}>
            <FontAwesomeIcon icon={faHouseChimney} /> Return Home
          </Link>

          {Array.isArray(favorites) && favorites.length > 0 ? (
            <>
              <h3 className="mt-5 px-3 py-1 to-color">
                Your Favorite images :
              </h3>
              <p className="px-3 h3-subtitle">
                Click on an image to see the prompt{" "}
              </p>
              <div className="image-container">
                {favorites.map((image) => (
                  <ImageCard
                    image={image}
                    key={image.imageID}
                    handleImageClick={handleImageClick}
                    handleCopyClick={handleCopyClick}
                    copiedImageId={copiedImageId}
                    selectedImages={selectedImages}
                  />
                ))}
              </div>
            </>
          ) : (
            <>
              <div className="p-5">
                <h2>Oops! Looks like you haven't liked anything yet.</h2>
                <p>Start by clicking the heart on the corner of any image.</p>
              </div>
            </>
          )}

          <br />
          <Link className="go-back-home p-3" to={"/"}>
            <FontAwesomeIcon icon={faHouseChimney} /> Return Home
          </Link>
        </div>
      </div>
    </tt>
  );
}

export default FavoritesPage;
