// src/main/resources/static/js/hash.js

/**
 * Generates a hash value from a given string.
 * @param {string} str - The input string to hash.
 * @returns {number} - The numerical hash value.
 */
function hashString(str) {
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
        const char = str.charCodeAt(i);
        hash = (hash << 5) - hash + char;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
}

// Expose the function to the global window object
window.hashString = hashString;
