export const sendPut = body => {
  fetch("/products", {
    method: "PUT",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    },
    body: body
  }).then(Response => {
    if (!Response.ok) alert("Error saving");
  });
};

export const sendPost = body => {
  fetch("/products", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    },
    body: body
  }).then(Response => {
    if (!Response.ok) alert("Error");
    else window.location.reload();
  });
};

export const sendDelete = id => {
  fetch("/products/" + id, {
    method: "DELETE",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json"
    }
  });
};
