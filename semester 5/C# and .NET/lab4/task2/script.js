const canvasElements = document.querySelectorAll('.drawing-canvas');

canvasElements.forEach((canvas) => {
    const ctx = canvas.getContext('2d');

    
    function drawLines(event) {
        const rect = canvas.getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;
        
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        
        // Draw lines from each corner to the mouse position
        ctx.beginPath();
        ctx.moveTo(0, 0);
        ctx.lineTo(x, y);
        ctx.moveTo(canvas.width, 0);
        ctx.lineTo(x, y);
        ctx.moveTo(0, canvas.height);
        ctx.lineTo(x, y);
        ctx.moveTo(canvas.width, canvas.height);
        ctx.lineTo(x, y);
        ctx.stroke();
    }

    // handle events
    canvas.addEventListener('mouseout', () => {
    
        ctx.clearRect(0, 0, canvas.width, canvas.height);
    });

    canvas.addEventListener('mousemove', drawLines);
});
