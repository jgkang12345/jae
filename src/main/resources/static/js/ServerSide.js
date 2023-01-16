async function post(url, data, header) {
    const response = await fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            ...header
        },
        body: JSON.stringify(data),
    });

    return await response.json();
}

async function get(url) {
    const response = await fetch(url);
    return await response.json();
}