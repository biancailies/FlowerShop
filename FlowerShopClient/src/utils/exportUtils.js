export const exportToCSV = (filename, data) => {
    if (!data || !data.length) return;
    const separator = ',';
    const keys = Object.keys(data[0]);
    const csvContent =
        keys.join(separator) +
        '\n' +
        data.map(row => {
            return keys.map(k => {
                let cell = row[k] === null || row[k] === undefined ? '' : row[k];
                cell = cell instanceof Date ? cell.toLocaleString() : cell.toString().replace(/"/g, '""');
                if (cell.search(/("|,|\n)/g) >= 0) {
                    cell = `"${cell}"`;
                }
                return cell;
            }).join(separator);
        }).join('\n');

    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    downloadBlob(blob, `${filename}.csv`);
};

export const exportToJSON = (filename, data) => {
    if (!data) return;
    const jsonContent = JSON.stringify(data, null, 2);
    const blob = new Blob([jsonContent], { type: 'application/json;charset=utf-8;' });
    downloadBlob(blob, `${filename}.json`);
};

export const exportToXML = (filename, data, rootName = 'data', itemName = 'item') => {
    if (!data || !data.length) return;
    const keys = Object.keys(data[0]);
    let xmlContent = `<?xml version="1.0" encoding="UTF-8"?>\n<${rootName}>\n`;
    data.forEach(row => {
        xmlContent += `  <${itemName}>\n`;
        keys.forEach(k => {
            let cell = row[k] === null || row[k] === undefined ? '' : row[k];
            xmlContent += `    <${k}>${cell}</${k}>\n`;
        });
        xmlContent += `  </${itemName}>\n`;
    });
    xmlContent += `</${rootName}>`;

    const blob = new Blob([xmlContent], { type: 'application/xml;charset=utf-8;' });
    downloadBlob(blob, `${filename}.xml`);
};

export const exportToDOC = (filename, title, data) => {
    if (!data || !data.length) return;
    const keys = Object.keys(data[0]);
    
    let htmlContent = `
    <html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:w='urn:schemas-microsoft-com:office:word' xmlns='http://www.w3.org/TR/REC-html40'>
    <head><meta charset='utf-8'><title>${title}</title></head>
    <body>
        <h1>${title}</h1>
        <table border="1" cellpadding="5" cellspacing="0" style="border-collapse: collapse;">
            <thead>
                <tr>
                    ${keys.map(k => `<th>${k}</th>`).join('')}
                </tr>
            </thead>
            <tbody>
                ${data.map(row => `
                    <tr>
                        ${keys.map(k => {
                            let cell = row[k] === null || row[k] === undefined ? '' : row[k];
                            return `<td>${cell}</td>`;
                        }).join('')}
                    </tr>
                `).join('')}
            </tbody>
        </table>
    </body>
    </html>`;

    const blob = new Blob([htmlContent], { type: 'application/msword;charset=utf-8;' });
    downloadBlob(blob, `${filename}.doc`);
};

const downloadBlob = (blob, filename) => {
    const link = document.createElement("a");
    if (link.download !== undefined) {
        const url = URL.createObjectURL(blob);
        link.setAttribute("href", url);
        link.setAttribute("download", filename);
        link.style.visibility = 'hidden';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }
};
