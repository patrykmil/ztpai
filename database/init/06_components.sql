-- Original components
INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (1, 'gradient text', 1, 2, 9, 3, '2025-03-02 14:00:00.000000',
e'.gradient-border {
    border: 2px solid transparent;
    border-radius: 5px;
    background-image: linear-gradient(white, white), linear-gradient(45deg, rgb(100, 61, 219) 0%, rgb(217, 21, 239) 100%);
    background-origin: border-box;
    background-clip: content-box, border-box;
    padding: 5px;
    font-size: 16px;
    font-weight: bold;
    color: transparent;
    -webkit-background-clip: text;
    background-clip: text;
    background-image: linear-gradient(45deg, rgb(100, 61, 219) 0%, rgb(217, 21, 239) 100%);
}',
        '&lt;input type=&quot;text&quot; class=&quot;gradient-border&quot; placeholder=&quot;Enter text here&quot;&gt;');

INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (2, 'pink submit', 3, 1, 10, 2, '2025-03-12 13:50:00.000000',
e'#submit-button {
    display: inline-flex;
    font-family: &quot;Segoe UI&quot;, Tahoma, Geneva, Verdana, sans-serif;
    width: 7.5em;
    height: 2em;
    font-size: 2rem;
    font-weight: 500;
    padding: 0.8rem 0.8rem;
    justify-content: center;
    align-items: center;
    gap: 0.625rem;
    border-radius: 0.8125rem;
    color: rgb(212, 58, 212);
    border: 3px solid rgb(212, 58, 212);
    background-color: inherit;
}

#submit-button:hover {
    background-color: rgb(212, 58, 212);
    color: #fff;
    cursor: pointer;
    border-color: #ffffff;
}',
        '&lt;button id=&quot;submit-button&quot;&gt;Submit&lt;/button&gt;');

INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (3, 'purple submit', 3, 1, 11, 2, '2025-03-13 12:48:00.000000',
e'#submit-button {
    display: inline-flex;
    font-family: &quot;Segoe UI&quot;, Tahoma, Geneva, Verdana, sans-serif;
    width: 7.5em;
    height: 2em;
    font-size: 2rem;
    font-weight: 500;
    padding: 0.8rem 0.8rem;
    justify-content: center;
    align-items: center;
    gap: 0.625rem;
    border-radius: 0.8125rem;
    color: #6a5ed3;
    border: 3px solid #6a5ed3;
    background-color: inherit;
}

#submit-button:hover {
    background-color: #6a5ed3;
    color: #fff;
    cursor: pointer;
    border-color: #ffffff;
}',
        '&lt;button id=&quot;submit-button&quot;&gt;Submit&lt;/button&gt;');

INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (4, 'green submit', 3, 1, 12, 2, '2025-03-14 12:50:00.000000',
e'#submit-button {
    display: inline-flex;
    font-family: &quot;Segoe UI&quot;, Tahoma, Geneva, Verdana, sans-serif;
    width: 7.5em;
    height: 2em;
    font-size: 2rem;
    font-weight: 500;
    padding: 0.8rem 0.8rem;
    justify-content: center;
    align-items: center;
    gap: 0.625rem;
    border-radius: 0.8125rem;
    color: #5ed398;
    border: 3px solid #5ed398;
    background-color: inherit;
}

#submit-button:hover {
    background-color: #5ed398;
    color: #000000;
    cursor: pointer;
    border-color: #000000;
}',
        '&lt;button id=&quot;submit-button&quot;&gt;Submit&lt;/button&gt;');

INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (5, 'green input', 3, 2, 12, 2, '2025-03-23 12:57:00.000000',
e'#search_input {
    display: inline-flex;
    font-family: &quot;Segoe UI&quot;, Tahoma, Geneva, Verdana, sans-serif;
    width: 7.5em;
    height: 2em;
    font-size: 1rem;
    font-weight: 500;
    padding: 0.1rem 0.8rem;
    justify-content: center;
    align-items: center;
    gap: 0.625rem;
    border-radius: 0.8125rem;
    color: #5ed398;
    border: 3px solid #5ed398;
    background-color: inherit;
}

#search_input:focus {
    outline: none;
    border: 3px solid #5ed398;
}',
        '&lt;input component_name=&quot;text&quot; id=&quot;search_input&quot; type=&quot;text&quot; placeholder=&quot;Search&quot; /&gt;');

-- New components
INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (6, 'blue checkbox', 7, 3, 13, 6, '2025-03-28 09:15:00.000000',
e'.custom-checkbox {
    appearance: none;
    width: 20px;
    height: 20px;
    border: 2px solid #3D85C6;
    border-radius: 4px;
    outline: none;
    cursor: pointer;
    position: relative;
}

.custom-checkbox:checked {
    background-color: #3D85C6;
}

.custom-checkbox:checked::after {
    content: "✓";
    position: absolute;
    color: white;
    font-size: 16px;
    top: -2px;
    left: 3px;
}',
'&lt;input type=&quot;checkbox&quot; class=&quot;custom-checkbox&quot; id=&quot;check1&quot;&gt;
&lt;label for=&quot;check1&quot;&gt;Accept terms&lt;/label&gt;');

INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (7, 'radio button group', 7, 4, 14, 6, '2025-04-01 10:30:00.000000',
e'.radio-container {
    display: flex;
    flex-direction: column;
    gap: 10px;
    font-family: Arial, sans-serif;
}

.radio-option {
    display: flex;
    align-items: center;
    gap: 8px;
}

.custom-radio {
    appearance: none;
    width: 18px;
    height: 18px;
    border: 2px solid #F1C232;
    border-radius: 50%;
    outline: none;
    cursor: pointer;
    position: relative;
}

.custom-radio:checked {
    border-color: #F1C232;
}

.custom-radio:checked::after {
    content: "";
    position: absolute;
    width: 10px;
    height: 10px;
    background-color: #F1C232;
    border-radius: 50%;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}',
'&lt;div class=&quot;radio-container&quot;&gt;
    &lt;div class=&quot;radio-option&quot;&gt;
        &lt;input type=&quot;radio&quot; name=&quot;size&quot; id=&quot;small&quot; class=&quot;custom-radio&quot;&gt;
        &lt;label for=&quot;small&quot;&gt;Small&lt;/label&gt;
    &lt;/div&gt;
    &lt;div class=&quot;radio-option&quot;&gt;
        &lt;input type=&quot;radio&quot; name=&quot;size&quot; id=&quot;medium&quot; class=&quot;custom-radio&quot;&gt;
        &lt;label for=&quot;medium&quot;&gt;Medium&lt;/label&gt;
    &lt;/div&gt;
    &lt;div class=&quot;radio-option&quot;&gt;
        &lt;input type=&quot;radio&quot; name=&quot;size&quot; id=&quot;large&quot; class=&quot;custom-radio&quot;&gt;
        &lt;label for=&quot;large&quot;&gt;Large&lt;/label&gt;
    &lt;/div&gt;
&lt;/div&gt;');

INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (8, 'black button', 6, 1, 7, 5, '2025-04-05 14:22:00.000000',
e'.dark-button {
    background-color: #1a1a1a;
    color: #ffffff;
    border: none;
    border-radius: 8px;
    padding: 12px 24px;
    font-family: "Segoe UI", sans-serif;
    font-size: 16px;
    font-weight: 500;
    cursor: pointer;
    transition: background-color 0.3s, transform 0.2s;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.dark-button:hover {
    background-color: #333333;
    transform: translateY(-2px);
    box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15);
}

.dark-button:active {
    transform: translateY(0);
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}',
        '&lt;button class=&quot;dark-button&quot;&gt;Dark Mode&lt;/button&gt;');

INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (9, 'minimalist input', 9, 2, 3, 8, '2025-04-08 11:45:00.000000',
e'.minimal-input {
    width: 100%;
    max-width: 300px;
    padding: 10px 0;
    border: none;
    border-bottom: 1px solid #ddd;
    font-family: "Arial", sans-serif;
    font-size: 16px;
    transition: border-color 0.3s;
    background-color: transparent;
}

.minimal-input:focus {
    outline: none;
    border-bottom: 1px solid #555;
}

.minimal-input::placeholder {
    color: #aaa;
}',
        '&lt;input type=&quot;text&quot; class=&quot;minimal-input&quot; placeholder=&quot;Enter your name&quot;&gt;');

INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (10, 'colorful button', 10, 1, 16, 9, '2025-04-10 16:20:00.000000',
e'.rainbow-button {
    background: linear-gradient(90deg, #ff0000, #ff7f00, #ffff00, #00ff00, #0000ff, #4b0082, #8b00ff);
    background-size: 200% 200%;
    border: none;
    border-radius: 25px;
    color: white;
    font-weight: bold;
    padding: 15px 30px;
    font-size: 18px;
    cursor: pointer;
    animation: rainbow 8s ease infinite;
    transition: transform 0.2s;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.rainbow-button:hover {
    transform: scale(1.05);
    box-shadow: 0 6px 20px rgba(0, 0, 0, 0.25);
}
',
'&lt;button class=&quot;rainbow-button&quot;&gt;Click Me!&lt;/button&gt;');

INSERT INTO public.component (component_id, component_name, set_id, type_id, color_id, user_id, created_at, css, html)
VALUES (11, 'basic input', 11, 2, 13, 4, '2025-05-12 09:10:00.000000',
e'.professional-input {
    width: 100%;
    max-width: 350px;
    padding: 12px 15px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-family: "Roboto", sans-serif;
    font-size: 14px;
    color: #333;
    transition: border-color 0.3s, box-shadow 0.3s;
}

.professional-input:focus {
    outline: none;
    border-color: #073763;
    box-shadow: 0 0 0 3px rgba(7, 55, 99, 0.2);
}

.input-label {
    display: block;
    margin-bottom: 8px;
    font-family: "Roboto", sans-serif;
    font-size: 14px;
    font-weight: 500;
    color: #333;
}',
'&lt;label class=&quot;input-label&quot; for=&quot;email&quot;&gt;Email Address&lt;/label&gt;
&lt;input type=&quot;email&quot; id=&quot;email&quot; class=&quot;professional-input&quot; placeholder=&quot;example@company.com&quot;&gt;');
