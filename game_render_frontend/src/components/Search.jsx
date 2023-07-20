import React, { useState, useEffect, useContext } from "react";
import ImageCard from "./ImageCard";
import {
  fetchImagesByKeyword,
  fetchUserFavorites,
  toggleFavorite,
} from "../api.js";
import { AuthContext } from "../AuthContext.js";
import copy from "clipboard-copy";
import "../css/ImageCard.css";

const Search = () => {
  const [input, setInput] = useState("");
  const [imageList, setImageList] = useState([]);
  const [selectedImages, setSelectedImages] = useState([]);
  const [favoriteImages, setFavoriteImages] = useState([]);
  const { user } = useContext(AuthContext);
  const [copiedImageId, setCopiedImageId] = useState(null);
  const [searchMade, setSearchMade] = useState(false);

  useEffect(() => {
    (async () => {
      if (user && user.username) {
        try {
          const favoriteImages = await fetchUserFavorites(user.username);
          setFavoriteImages(favoriteImages);
        } catch (error) {
          console.error("Failed to fetch favorite images:", error);
        }
      }
    })();
  }, [user]);

  const handleSearch = async () => {
    try {
      const images = await fetchImagesByKeyword(input);
      setImageList(images);
      setSearchMade(true);
    } catch (error) {
      console.error("Failed to fetch images:", error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    handleSearch();
  };

  const handleClearResults = () => {
    setImageList([]);
    setInput("");
    setSearchMade(false);
  };

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

  const handleFavoriteToggle = async (imageID) => {
    try {
      await toggleFavorite(user.username, imageID);
      const updatedFavoriteImages = [...favoriteImages];
      if (updatedFavoriteImages.some((img) => img.imageID === imageID)) {
        // Remove from favorites
        const index = updatedFavoriteImages.findIndex(
          (img) => img.imageID === imageID
        );
        updatedFavoriteImages.splice(index, 1);
      } else {
        // Add to favorites
        const image = imageList.find((img) => img.imageID === imageID);
        updatedFavoriteImages.push(image);
      }
      setFavoriteImages(updatedFavoriteImages);
    } catch (error) {
      console.error("Failed to toggle favorite image:", error);
    }
  };

  return (
    <div className="category-page-container text-white">
      <tt>
        <h2 className="p-3 "> Or search any image : </h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            value={input}
            onChange={(e) => setInput(e.target.value)}
            className="mx-3 mb-3"
          />
          <button type="submit">Search</button>
          {imageList.length > 0 && (
            <button type="button" onClick={handleClearResults}>
              Clear Results
            </button>
          )}
        </form>
        <div className="image-container">
          {imageList.length > 0 ? (
            imageList.map((image) => (
              <ImageCard
                image={image}
                key={image.imageID}
                handleImageClick={handleImageClick}
                handleCopyClick={handleCopyClick}
                copiedImageId={copiedImageId}
                selectedImages={selectedImages}
                isFavorite={favoriteImages.some(
                  (img) => img.imageID === image.imageID
                )}
                handleFavoriteToggle={handleFavoriteToggle}
              />
            ))
          ) : searchMade ? (
            <p>Ops! No image found for this description. Try something else?</p>
          ) : null}
        </div>
      </tt>
    </div>
  );
};

export default Search;
