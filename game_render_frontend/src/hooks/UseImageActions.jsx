import { useState, useEffect } from "react";
import { fetchUserFavorites, toggleFavorite } from "../api";
import copy from "clipboard-copy";

export const UseImageActions = (user, images) => {
  const [favoriteImages, setFavoriteImages] = useState([]);
  const [copiedImageId, setCopiedImageId] = useState(null);

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
        const image = images.find((img) => img.imageID === imageID);
        updatedFavoriteImages.push(image);
      }
      setFavoriteImages(updatedFavoriteImages);
    } catch (error) {
      console.error("Failed to toggle favorite image:", error);
    }
  };

  return {
    favoriteImages,
    setFavoriteImages,
    copiedImageId,
    handleCopyClick,
    handleFavoriteToggle,
  };
};
